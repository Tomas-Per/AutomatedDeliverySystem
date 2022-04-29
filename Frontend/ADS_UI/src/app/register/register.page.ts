import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.page.html',
  styleUrls: ['./register.page.scss'],
})
export class RegisterPage implements OnInit {

  constructor(private router: Router) { }

  ngOnInit() {
  }

  navigateToLogin() {
    this.router.navigate(['/login']);
  }

  signUp() {
    // sign up logic here
    this.router.navigate(['/login']);
  }
}
