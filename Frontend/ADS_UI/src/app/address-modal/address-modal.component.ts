import { Component, Input, OnInit } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { ModalController } from '@ionic/angular';
import { Address } from '../models/address';
import { AddressPreview } from '../models/addressPreview';
import { User } from '../models/user';

@Component({
  selector: 'app-address-modal',
  templateUrl: './address-modal.component.html',
  styleUrls: ['./address-modal.component.scss'],
})
export class AddressModalComponent implements OnInit {

  @Input() address: Address;

  nameInput = new FormControl('', Validators.required);
  lastNameInput = new FormControl('', Validators.required);
  phoneNumberInput = new FormControl('', [Validators.required, Validators.pattern('^[0-9]+$')]);
  streetInput = new FormControl('', Validators.required);
  houseNumberInput = new FormControl('', Validators.required);
  countryInput = new FormControl('', Validators.required);
  cityInput = new FormControl('', Validators.required);
  postalCodeInput = new FormControl('', Validators.required);
  constructor(private modalController: ModalController) { }

  dismissModal() {
    this.modalController.dismiss(null, 'cancel');
  }

  onSave() {
    // const newName = this.nameInput.value;
    this.modalController.dismiss(this.address, 'saved');
  }

  ngOnInit() {
    // this.ionicForm = this.formBuilder.group({
    //   firstName: ['', [Validators.required, Validators.minLength(2)]],
    //   lastName: ['', [Validators.required, Validators.minLength(2)]],
    //   phoneNumber: ['', [Validators.required, Validators.pattern('^[0-9]+$')]]
    // });
  }

}
