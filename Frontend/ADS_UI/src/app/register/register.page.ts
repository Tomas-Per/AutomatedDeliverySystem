import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { ToastService } from '../services/toast.service';
import { StorageService } from '../services/storage.service';
import { AuthConstants } from '../auth-constants';
import { RegisterModel } from '../models/register';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {

  postData = new RegisterModel();

  constructor(private router: Router,
    private authService: AuthService,
    private toastService: ToastService,
    private storageService: StorageService
  ) {}

  ngOnInit() {
  }

  validateInputs() {
    return (
    this.postData.email &&
    this.postData.password
    );
  }

  navigateToLogin() {
    this.router.navigate(['/login']);
  }

  signUp() {
    if (this.validateInputs()) {
      this.authService.signup(this.postData).subscribe(
        (res: any) => {
          if (res) {
            // Storing the User data.
            this.storageService
              .store(AuthConstants.AUTH, res)
              .then(_res => {
              this.router.navigate(['/login']);
              });
          } else {
            this.toastService.presentToast('Email is already taken, please enter new details.');
          }
        },
        (error: any) => {
          this.toastService.presentToast('A network problem occured.');
          }
        );
        } else {
          this.toastService.presentToast('Please enter a valid email and password.');
        }
  }
}
