import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { StorageService } from '../services/storage.service';

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage implements OnInit{

  packages = [];

  constructor(private router: Router,
    private storageService: StorageService
    ) {}

  ngOnInit() {
    this.storageService.get('userData').then((data)=>{
      console.log(data.phoneNumber);
    });
  }

  getPackages() {
    // get package previews
  }

  redirectToPackage() {
    // specific package
  }

  navigateToDeliveryRegister() {
    this.router.navigate(['/register-delivery']);
  }

}
