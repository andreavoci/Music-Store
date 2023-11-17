import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/public/services/auth.service';
import { LoginResponse, Util } from 'src/app/public/services/util';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  public token : string | null = null;

  constructor(private http: HttpClient,private router: Router) { }

  ngOnInit(): void {
    this.token = AuthService.getToken("token")
    if(this.token){
      this.router.navigate(['/profile']);
    }
  }
  register(form: any) {
    if(form["password"]!=form["confirm-password"]){
      let element = document.getElementById("loginform-error")
      if (element != null) {
        element.innerHTML = "Password do not match"
      }
      return
    }
    if(!form["username"] || !form["email"] || !form["password"] || !form["confirm-password"]){
      let element = document.getElementById("loginform-error")
      if (element != null) {
        element.innerHTML = "Empty spaces!"
      }
      return
    }
    this.http.post<LoginResponse>(Util.authServerUrl + "/register", form).subscribe(
      success => {
        console.log(success.token)
        AuthService.setToken("token", success.token)
        AuthService.setToken("id", success.id)
        window.location.reload()
      },
      error => {
        console.log(error.error)
        let element = document.getElementById("loginform-error")
        if (element != null) {
          element.innerHTML = error.error
        }
      }
    )
  }

}
