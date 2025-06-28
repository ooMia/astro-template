import { describe, expect, test } from "@jest/globals";
import { Endpoint, parseEndpointsToPath, toUrl } from "./fetcher";

describe("fetcher::toUrl", () => {
  test("should return correct URL without port", () => {
    const endpoint: Endpoint = {
      protocol: "https",
      host: "example.com",
      method: "GET",
      path: "/api/data",
    };
    expect(toUrl(endpoint)).toBe("https://example.com/api/data");
  });

  test("should return correct URL with port", () => {
    const endpoint: Endpoint = {
      protocol: "http",
      host: "localhost",
      port: 3000,
      method: "GET",
      path: "/api/data",
    };
    expect(toUrl(endpoint)).toBe("http://localhost:3000/api/data");
  });
});

describe("fetcher::parseEndpointsToPath", () => {
  test("should return correct path for remote endpoint", () => {
    const endpoint: Endpoint = {
      protocol: "https",
      host: "jsonplaceholder.typicode.com",
      method: "GET",
      path: "/todos/2",
    };
    expect(parseEndpointsToPath(endpoint)).toBe(
      "jsonplaceholder-typicode-com/todos/2.json"
    );
  });

  test("should return correct path for localhost with port", () => {
    const endpoint: Endpoint = {
      protocol: "http",
      host: "localhost",
      port: 3000,
      method: "GET",
      path: "/api/data",
    };
    expect(parseEndpointsToPath(endpoint)).toBe("localhost/api/data.json");
  });
});


