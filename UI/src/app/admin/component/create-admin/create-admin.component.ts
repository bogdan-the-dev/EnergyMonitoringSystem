import {Component, OnInit} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UserService} from "../../../service/user.service";
import {PasswordMatchValidator} from "../../../authentification/PasswordMatch.validator";
import {UserRegisterModel} from "../../../authentification/model/userRegister.model";
import {AuthService} from "../../../authentification/service/auth.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-create-admin',
  templateUrl: 'create-admin.component.html',
  styleUrls: ['create-admin.component.less']
})
export class CreateAdminComponent implements OnInit{
  registerForm: FormGroup

  constructor(private authService: AuthService, private route: ActivatedRoute, private router: Router) {
  }

  ngOnInit() {
    this.initForm()
  }
  initForm() {
    this.registerForm = new FormGroup({
      'username': new FormControl(null, Validators.required),
      'email': new FormControl(null, [Validators.required, Validators.email]),
      'password': new FormControl(null, [Validators.required, Validators.minLength(8)]),
      'confirmPassword': new FormControl(null, [Validators.required, Validators.minLength(8)])
    }, {
      validators: PasswordMatchValidator
    })
  }

  onSubmit(){
    let user: UserRegisterModel = {
      username: this.registerForm.get('username').value,
      email: this.registerForm.get('email').value,
      password: this.registerForm.get('password').value
    }
    this.authService.registerAdmin(user).subscribe(res => {
      if(res === 'done') {
        this.initForm()
      }
    })
    this.router.navigate(['../'], {relativeTo: this.route})
  }

}
