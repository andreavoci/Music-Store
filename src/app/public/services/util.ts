export namespace Util {

  export const serverUrl = 'http://localhost:9090';
  export const userServerUrl = 'http://localhost:9090/api/user';
  export const authServerUrl = 'http://localhost:9090/api/auth';
  export const cartServerUrl = 'http://localhost:9090/api/cart';
  export const ordersServerUrl = 'http://localhost:9090/api/orders';
  export const productServerUrl = 'http://localhost:9090/api/product';

}
export interface LoginResponse{
  id:string,
  token:string
}
