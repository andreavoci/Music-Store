export interface Product{
  id: number;
  isrc: number;
  title: string;
  artist: string;
  releaseDate:Date;
  genre: string;
  cd:number;
  tracklist: string;
  cover: string;
  optLock: number;
  price: number;
  stock: number;
}
export class Product{
  constructor(
    public isrc: number,
    public title: string,
    public artist: string,
    public releaseDate:Date,
    public genre: string,
    public cd:number,
    public tracklist: string,
    public cover:string,
    public optLock: number,
    public price: number,
    public stock: number){}
}



