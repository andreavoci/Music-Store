import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/public/services/auth.service';
import { LoginResponse, Util } from 'src/app/public/services/util';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  public token : string | null = null;
  constructor(private http: HttpClient, private router: Router){ }

  ngOnInit(): void {
    this.token = AuthService.getToken("token")
    if(this.token){
      this.router.navigate(['/profile']);
    }
  }

  login(form: any){
    // console.log(data)
    this.http.post<LoginResponse>(Util.authServerUrl+"/login",form).subscribe(
      success => {
        console.log(success.token)
        AuthService.setToken("token",success.token)
        AuthService.setToken("id",success.id)
        window.location.href = '/profile'
      },
      error => {
        console.log(error.error)
        let element = document.getElementById("loginform-error")
        if(element != null){
          element.innerHTML = error.error
        }
      }
    )
  }

  register(form: any){
    this.http.post<LoginResponse>(Util.authServerUrl+"/register",form).subscribe(
      success=>{
        console.log(success.token)
        AuthService.setToken("token",success.token)
        AuthService.setToken("id",success.id)
        window.location.reload()
      },
      error=>{console.log(error.error)
        let element = document.getElementById("loginform-error")
        if(element != null){
          element.innerHTML = error.error
        }
      }
    )
  }

  logout(){
     console.log("delete")
    AuthService.deleteToken("token")
    window.location.reload()
  }

  navigateToRegistration() {
    this.router.navigate(['/registration']);
  }

}
