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

async function fetchEndpointData(endpoint: Endpoint): Promise<any> {
  const url = toUrl(endpoint);
  return await fetch(url, { method: endpoint.method })
    .then((res) => res.json())
    .catch((error) => {
      console.error(`Error fetching data from ${url}: `, endpoint, error);
    });
}

async function writeResponseToFile(data: any, filePath: string): Promise<void> {
  const jsonData = JSON.stringify(data, null, 2);
  
  const __filename = fileURLToPath(import.meta.url);
  const __dirname = path.dirname(__filename);

  const baseDir = path.resolve(__dirname, "..", "..", "data");
  const fullFilePath = path.join(baseDir, filePath);
  const dir = path.dirname(fullFilePath);

  await fs.promises.mkdir(dir, { recursive: true });
  await fs.promises.writeFile(fullFilePath, jsonData, "utf-8");
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

async function main() {
  console.log("Fetcher started...");

  const endpointConfig: EndpointsConfig = await Promise.resolve(
    fs.readFileSync("endpoints.json", "utf-8")
  )
    .then((data) => JSON.parse(data))
    .catch((error) => {
      console.error("Error reading endpoints.json:", error);
      throw error;
    });

  const endpoints: Endpoint[] = endpointConfig.endpoints;

  for (const endpoint of endpoints) {
    try {
      const data = await fetchEndpointData(endpoint);
      const filePath = parseEndpointsToPath(endpoint);
      await writeResponseToFile(data, filePath);
      console.log(`Data saved to: ${filePath}`);
    } catch (error) {
      console.error("Error processing endpoint:", endpoint, error);
    }
  }
}

if (import.meta.url === `file://${process.argv[1]}`) {
  main().catch(console.error);
}

export { Endpoint, parseEndpointsToPath, toUrl };
