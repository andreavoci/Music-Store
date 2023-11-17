import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
    constructor(){}
    static setToken(key:string, value:string){
        localStorage.setItem(key, value);
    }

    static getToken(key:string): string | null{
        let token = localStorage.getItem( key );
    return token;
    }

    static deleteToken(key:string): void{
        let token = localStorage.removeItem(key);
    }
}
