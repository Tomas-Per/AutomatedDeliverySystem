import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { StorageService } from '../services/storage.service';
import { AuthConstants } from '../auth-constants';
import { LoggedInModel } from '../models/logged-in';
import { LogInModel } from '../models/log-in';
import { ToastService } from '../services/toast.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.page.html',
  styleUrls: ['./login.page.scss'],
})
export class LoginPage implements OnInit {

  postData = new LogInModel();

  constructor(private router: Router,
    private authService: AuthService,
    private storageService: StorageService,
    private toastService: ToastService
  ) {}

  ngOnInit() {
  }

  validateInputs() {
    return (
    this.postData.email &&
    this.postData.password
    );
  }

  navigateToRegister() {
    this.router.navigate(['/register']);
  }

  logIn() {
    if (this.validateInputs()) {
      this.authService.login(this.postData).subscribe(
        (res: any) => {
          if (res) {
          // Storing the User data.
          this.storageService.store(AuthConstants.AUTH, res);
          this.router.navigate(['/home']);
          } else {
            this.toastService.presentToast('Incorrect login credentials.');
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
