import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PasswordMatchValidator} from "../../PasswordMatch.validator";
import {AuthService} from "../../service/auth.service";
import {Router} from "@angular/router";
import {UserRegisterModel} from "../../model/userRegister.model";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.less']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;

  constructor(private authService: AuthService, private router: Router) {
  }

  ngOnInit(): void {
    this.initForm();
  }

  private initForm() {
    let username = '';
    let email = '';
    let password = '';
    let confirmPassword = '';

    this.registerForm = new FormGroup({
      'username': new FormControl(username, Validators.required),
      'email': new FormControl(email, [Validators.required, Validators.email]),
      'password': new FormControl(password, [Validators.required, Validators.minLength(8)]),
      'confirmPassword': new FormControl(confirmPassword, [Validators.required, Validators.minLength(8)])
    }, {
      validators: PasswordMatchValidator
    })
  }

  onSubmit() {
    let username = this.registerForm.get('username').value
    let email = this.registerForm.get('email').value
    let password = this.registerForm.get('password').value
    let user: UserRegisterModel = {username: username, password: password, email: email}
    this.authService.registerUser(user).subscribe(res => {
      console.log(res)
      this.router.navigate(['auth','login'])

    })

  }
}
