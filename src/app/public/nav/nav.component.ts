import { Component, OnInit } from '@angular/core';
import { AuthService } from '../services/auth.service';
import { User } from 'src/app/models/user';
import { HttpClient } from '@angular/common/http';
import { Util } from '../services/util';
import { Genre } from 'src/app/models/genre.enum';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.css']
})
export class NavComponent implements OnInit {

  public token : string | null = null;
  public userId = -1;
  user : User |null = null;
  genres: string[] = this.enumValues(Genre);

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.token = AuthService.getToken("token")
    if(AuthService.getToken("id")){
      this.userId = Number(AuthService.getToken("id"))
    }
    this.checkUser()
  }

  checkUser(){
    if(this.token){
      this.http.get<User>(Util.userServerUrl+"/"+this.userId).subscribe( result=> {
        this.user = result;
    });
    }
  }

  enumValues(enumType: any): string[]{
    return Object.keys(enumType).map(key => enumType[key]);
}
}
