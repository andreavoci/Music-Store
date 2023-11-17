
export interface Request{
  id:number;
  body:any;
}
export class Request {
  constructor(public id: number, public body: any) {}
}
