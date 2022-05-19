import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-tracking-search',
  templateUrl: './tracking-search.page.html',
  styleUrls: ['./tracking-search.page.scss'],
})
export class TrackingSearchPage implements OnInit {
  opts= ['', '', '', '', '', '', '', ''];
  constructor() { }

  ngOnInit() {
  }
  _searchTracking(event) {
    console.log(event.detail.value);
  }
  next(el) {
    el.setFocus();
  }
  track() {
    const str = this.opts.join('');
    console.log(str);
  }
}
