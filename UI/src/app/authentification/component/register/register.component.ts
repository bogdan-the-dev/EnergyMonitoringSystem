import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PasswordMatchValidator} from "../../PasswordMatch.validator";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.less']
})
export class RegisterComponent implements OnInit {
  // @ts-ignore
  registerForm: FormGroup;

  constructor() { }

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

  }

}
