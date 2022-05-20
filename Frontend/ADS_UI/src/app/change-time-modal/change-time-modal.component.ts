import { Component, Input, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { EditOrder } from '../models/editOrder';
import { OrderService } from '../services/order.service';

@Component({
  selector: 'app-change-time-modal',
  templateUrl: './change-time-modal.component.html',
  styleUrls: ['./change-time-modal.component.scss'],
})
export class ChangeTimeModalComponent implements OnInit {
  editableDate = new EditOrder();

  // eslint-disable-next-line @typescript-eslint/member-ordering
  @Input() estimatedDate: string;

  constructor(private modalController: ModalController,
    private orderService: OrderService) { }

  ngOnInit() {
    console.log(this.estimatedDate);
    this.editableDate.convenientArrivalTimeFrom = this.estimatedDate;
    this.editableDate.convenientArrivalTimeTo = this.estimatedDate;
  }

  dismissModal() {
    this.modalController.dismiss(null, 'closed');
  }
  saveTime() {
    console.log(this.editableDate);
    this.modalController.dismiss(null, 'updated');
  }

}
