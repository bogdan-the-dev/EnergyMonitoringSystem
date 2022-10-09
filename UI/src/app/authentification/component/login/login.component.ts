import { Component, OnInit } from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.less']
})
export class LoginComponent implements OnInit {
  // @ts-ignore
  authForm: FormGroup;

  constructor() { }

  ngOnInit(): void {
    this.initForm();
  }

  onSubmit() {

  }

  private initForm() {
    let username = '';
    let password = '';

    this.authForm = new FormGroup({
      'username': new FormControl(username, Validators.required),
      'password': new FormControl(password, Validators.required)
    })
  }
}
