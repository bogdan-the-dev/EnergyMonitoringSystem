import {Component, OnInit} from "@angular/core";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {PasswordMatchValidator} from "../../authentification/PasswordMatch.validator";
import {Store} from "@ngrx/store";
import {UserRegisterModel} from "../../authentification/model/userRegister.model";
import {AuthService} from "../../authentification/service/auth.service";
import {SharedActions} from "../../shared/state-management/shared.actions";
import {Router} from "@angular/router";

@Component({
  selector: 'app-edit-account',
  templateUrl: 'edit-account.html',
  styleUrls: ['edit-account.component.less']
})
export class EditAccountComponent implements OnInit{
  editForm: FormGroup
  username

  constructor(private store: Store<any>, private authService: AuthService, private router: Router) {
  }

  ngOnInit() {
    this.store.select(s => s.sharedState.loginUsername).subscribe(res => {
      this.username = res
    })
    this.initForm()
  }
  initForm() {
    this.editForm = new FormGroup({
      'password': new FormControl(null, [Validators.required, Validators.minLength(8)]),
      'confirmPassword': new FormControl(null, [Validators.required, Validators.minLength(8)])
    }, {
      validators: PasswordMatchValidator
    })
  }

  onSubmit() {
    let user: UserRegisterModel = {
      username: this.username,
      email: '',
      password: this.editForm.get('password').value
    }
    this.authService.changePassword(user).subscribe()
    this.initForm()
    this.router.navigate(['/home'])
  }
  onDelete() {
    console.log('delete')
    this.authService.deleteUser(this.username).subscribe()
    this.store.dispatch({type: SharedActions.LOGOUT, payload: true})
    this.router.navigate(['/home'])
  }
}
