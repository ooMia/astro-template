import { fetchDataWithRetry } from "./fetchData";

export class Metadata {
  static readonly chapters = new Map<string, number>([["expression", 2]]);
  static getListChapters(): string[] {
    return Array.from(Metadata.chapters.keys());
  }
  static getChapterIdFromChapterName(chapterName: string): number {
    return Metadata.chapters.get(chapterName) ?? 0;
  }
  static async create(chapterName: string): Promise<Metadata> {
    const instance = new Metadata(chapterName);
    await instance.initialize();
    return instance;
  }

  chapterName: string;
  chapterId: number;
  mistakes: { from: number; to: number; length: number } = {} as any;
  examples: Map<number, number> = new Map();
  private constructor(chapterName: string) {
    this.chapterName = chapterName;
    this.chapterId = Metadata.getChapterIdFromChapterName(chapterName);
  }

  async initialize() {
    // extract all ids from all mistakes
    const { result, length } = await fetchDataWithRetry(
      `api/${this.chapterName}`,
    ).then((res) => res.data);
    if (result === undefined || length === undefined || length < 1) {
      return this;
    }

    const mistakeIds = result.map((x) => x.id);
    const filtered = mistakeIds
      // 1. filter by chapterId: 2_%d_%d
      .filter((id) => {
        const reg = new RegExp(`^${this.chapterId}_\\d+_\\d+$`);
        return reg.test(id);
      });
    // 2. extract mistake range: min, max, length
    const min = Math.min(...filtered.map((id) => parseInt(id.split("_")[1])));
    const max = Math.max(...filtered.map((id) => parseInt(id.split("_")[1])));
    this.mistakes = { from: min, to: max, length: max - min + 1 };

    // 3. for each mistake id, get example max index as length
    filtered.forEach((id) => {
      const mistakeId = parseInt(id.split("_")[1]);
      const exampleIndex = parseInt(id.split("_")[2]);
      // replace if current example index is bigger: result is the biggest example index
      const cur = this.examples.get(mistakeId) ?? -1;
      if (cur < 1 || cur < exampleIndex) {
        this.examples.set(mistakeId, exampleIndex);
      }
    });
    return this;
  }

  getEndpoint(mistakeId?: number, exampleIndex?: number): string {
    if (mistakeId === undefined) {
      exampleIndex = undefined;
    }
    return ["/api", this.chapterName, this.chapterId, mistakeId, exampleIndex]
      .filter((x) => x !== undefined)
      .join("/");
  }

  getExampleLength(mistakeId: number): number {
    return this.examples.get(mistakeId) ?? 0;
  }
  getMistakeLength(): number {
    return this.mistakes.length;
  }
  getMistakeRange(): { from: number; to: number } {
    const { from, to } = this.mistakes;
    return { from, to };
  }
}
