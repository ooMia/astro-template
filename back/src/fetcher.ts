import fs from "fs";
import path from "path";
import { fileURLToPath } from "url";

interface EndpointsConfig {
  version: string;
  endpoints: Endpoint[];
}

interface Endpoint {
  protocol: string;
  host: string;
  port?: number;
  method: string;
  path: string;
  description?: string;
}

function toUrl(endpoint: Endpoint): string {
  const portPart = endpoint.port ? `:${endpoint.port}` : "";
  return `${endpoint.protocol}://${endpoint.host}${portPart}${endpoint.path}`;
}

function parseEndpointsToPath(endpoint: Endpoint): string {
  const hostPath = endpoint.host.replace(/\./g, "-");
  const fileName = endpoint.path.replace(/^\//, "");
  return `${hostPath}/${fileName}.json`;
}

export { Endpoint, parseEndpointsToPath, toUrl };

async function fetchEndpointData(endpoint: Endpoint): Promise<any> {
  const url = toUrl(endpoint);
  return await fetch(url, { method: endpoint.method }).then((res) =>
    res.json()
  );
}

async function writeResponseToFile(data: any, filePath: string): Promise<void> {
  if (data === undefined || data === null) {
    return;
  }

  const __filename = fileURLToPath(import.meta.url);
  const __dirname = path.dirname(__filename);
  const baseDir = path.resolve(__dirname, "..", "..", "data");
  const fullFilePath = path.join(baseDir, filePath);
  const dir = path.dirname(fullFilePath);
  await fs.promises.mkdir(dir, { recursive: true });

  const jsonData = JSON.stringify(data, null, 2);
  await fs.promises.writeFile(fullFilePath, jsonData, "utf-8");
}

async function main() {
  console.log("Fetcher started...");

  const endpointConfig: EndpointsConfig = JSON.parse(
    fs.readFileSync("endpoints.json", "utf-8")
  );

  const endpoints: Endpoint[] = endpointConfig.endpoints;
  console.log("Total requests:", endpoints.length);

  // Promise.all을 사용하면 실패한 요청의 오류가 콘솔로 전파됩니다.
  await Promise.allSettled(
    endpoints.map(async (endpoint) => {
      const data = await fetchEndpointData(endpoint);
      const filePath = parseEndpointsToPath(endpoint);
      await writeResponseToFile(data, filePath);
      console.log(data);
      return data;
    })
  )
    .then((results: PromiseSettledResult<any>[]) =>
      results.filter((result) => result.status === "fulfilled")
    )
    .then((responses) => {
      const total = endpoints.length;
      const count = responses.length;
      const percent = Math.round((count / total) * 100);
      console.log(`Total responses: ${count}/${total} (${percent}%)`);
    });
}

if (import.meta.url === `file://${process.argv[1]}`) {
  main().catch(console.error);
}
