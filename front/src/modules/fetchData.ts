interface Response {
  message?: string;
  result?: {
    id: string;
    message: string;
    before: string;
    after: string;
    path: string;
  }[];
  length?: number;
}

async function _fetchWithRetry(
  url: string,
  retriesLeft: number,
  maxRetries: number = 3,
  delayMs: number = 10000
) {
  try {
    const response = await fetch(url);
    if (!response.ok) {
      throw new Error(`HTTP Status: ${response.status}`);
    }
    return await response.json();
  } catch (error) {
    if (retriesLeft <= 0) throw error;

    console.log(`Retrying... (${maxRetries - retriesLeft + 1}/${maxRetries})`);
    await new Promise((resolve) => setTimeout(resolve, delayMs));
    return _fetchWithRetry(url, retriesLeft - 1);
  }
}

export async function fetchDataWithRetry(
  apiPath: string,
  baseUrl: string = "http://localhost:3000",
  maxRetries: number = 3,
  delayMs: number = 10000
): Promise<{ data: Response; message: string }> {
  let data = { message: "No Data" };
  let message = "No Data";
  try {
    const url = `${baseUrl}/${apiPath}`;
    // console.log(`fetchDataWithRetry(): ${url}`);
    data = await _fetchWithRetry(url, maxRetries, delayMs);
    message = data.message || "Data Loaded Successfully";
  } catch (error) {
    if (error instanceof Error) {
      data = { message: error.message };
      message = error.message;
    }
  }
  return { data, message };
}
