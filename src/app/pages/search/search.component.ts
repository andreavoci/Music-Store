import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  string: any;

  constructor(private http: HttpClient,private router: Router) { }

  ngOnInit() {
  }

  doSearch(value: string){
    const encodedValue = encodeURIComponent(value);
    this.router.navigateByUrl(`/search/${encodedValue}`);
  }

}
