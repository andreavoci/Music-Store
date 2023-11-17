import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductGenreComponent } from './product-genre.component';

describe('ProductGenreComponent', () => {
  let component: ProductGenreComponent;
  let fixture: ComponentFixture<ProductGenreComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductGenreComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ProductGenreComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
