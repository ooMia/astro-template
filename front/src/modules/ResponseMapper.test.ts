import { expect, suite, test } from "vitest";
import { Metadata } from "./Metadata";

function _testDescription(get: string, from: string): string {
  return `get ${get.padEnd(14)} from ${from}`;
}

interface Response {
  result: {
    message: string;
    before: string;
    after: string;
    chapter: number;
    id: string;
    className: string;
    path: string;
  }[];
  length: number;
}

const response: Response = {
  result: [
    {
      message: "com.example.mistakes.expression._01_OperationPriority.Ex4",
      before:
        "    int before(int bits) {\n      return bits & 0xFF00 + 1;\n    }\n",
      after:
        "    int after(int bits) {\n      return (bits & 0xFF00) | 1;\n    }\n",
      chapter: 2,
      id: "2_01_4",
      className: "Ex4",
      path: "file:///Users/mia/100_java_mistakes/back/src/main/java/com/example/mistakes/expression/_01_OperationPriority.java",
    },
  ],
  length: 1,
};
const { message, before, after, chapter, id, className, path } =
  response.result[0];

suite("parse property", () => {
  test(_testDescription("chapterId", "id"), async () => {
    function _getChapterIdFromId(id: string): number {
      const reg = /(\d+)_\d+_\d+/;
      const match = RegExp(reg).exec(id);
      if (match === null) {
        return 0;
      }
      return parseInt(match[1]);
    }
    const res = _getChapterIdFromId(id);
    expect(res).toBe(2);
  });

  test(_testDescription("mistakeId", "id"), async () => {
    function _getMistakeIdFromId(id: string): number {
      const reg = /\d+_(\d+)_\d+/;
      const match = RegExp(reg).exec(id);
      if (match === null) {
        return 0;
      }
      return parseInt(match[1]);
    }
    const res = _getMistakeIdFromId(id);
    expect(res).toBe(1);
  });

  test(_testDescription("exampleIndex", "id"), async () => {
    function _getExampleIndexFromId(id: string): number {
      const reg = /\d+_\d+_(\d+)/;
      const match = RegExp(reg).exec(id);
      if (match === null) {
        return 0;
      }
      return parseInt(match[1]);
    }
    const res = _getExampleIndexFromId(id);
    expect(res).toBe(4);
  });

  test(_testDescription("chapterName", "path"), async () => {
    function _getChapterNameFromPath(path: string): string {
      const reg = /\/mistakes\/(.+?)\//;
      const match = RegExp(reg).exec(path);
      if (match === null) {
        return "";
      }
      return match[1];
    }
    const res = _getChapterNameFromPath(path);
    expect(res).toBe("expression");
  });

  test(_testDescription("mistakeId", "path"), async () => {
    function _getMistakeIdFromPath(path: string): number {
      const reg = /\/mistakes\/.+?\/_(\d+)_/;
      const match = RegExp(reg).exec(path);
      if (match === null) {
        return 0;
      }
      return parseInt(match[1]);
    }
    const res = _getMistakeIdFromPath(path);
    expect(res).toBe(1);
  });

  test(_testDescription("exampleContext", "path"), async () => {
    function _getExampleContextFromPath(path: string): string {
      const reg = /\/mistakes\/.+?\/_\d+_(.+)\.java/;
      const match = RegExp(reg).exec(path);
      if (match === null) {
        return "";
      }
      return match[1];
    }
    const res = _getExampleContextFromPath(path);
    expect(res).toBe("OperationPriority");
  });

  test(_testDescription("exampleIndex", "className"), async () => {
    function _getExampleIndexFromClassName(className: string): number {
      const reg = /Ex(\d+)/;
      const match = RegExp(reg).exec(className);
      if (match === null) {
        return 0;
      }
      return parseInt(match[1]);
    }
    const res = _getExampleIndexFromClassName(className);
    expect(res).toBe(4);
  });
});

suite("meta builder", async () => {
  const instance = await Metadata.create("expression");

  test("static", async () => {
    expect(Metadata.getListChapters()).toEqual(["expression"]);
    expect(Metadata.getChapterIdFromChapterName("expression")).toBe(2);
  });

  test("initialize", async () => {
    expect(instance.chapterName).toBe("expression");
    expect(instance.chapterId).toBe(2);
    expect(instance.getMistakeRange()).toEqual({ from: 1, to: 14 });
    expect(instance.getMistakeLength()).toBe(14);
    expect(instance.getExampleLength(1)).toBe(4);
    expect(instance.getExampleLength(7)).toBe(3);
    expect(instance.getExampleLength(11)).toBe(7);
  });

  test("getEndpoint()", async () => {
    expect(instance.getEndpoint()).toBe("/api/expression/2");
    expect(instance.getEndpoint(2)).toBe("/api/expression/2/2");
    expect(instance.getEndpoint(2, 4)).toBe("/api/expression/2/2/4");
    expect(instance.getEndpoint(undefined, 4)).toBe("/api/expression/2");
  });
});
