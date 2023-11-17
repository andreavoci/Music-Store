import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ProductListComponent } from './pages/product-list/product-list.component';
import { ShoppingCartComponent } from './pages/shopping-cart/shopping-cart.component';
import { ProductDetailComponent } from './pages/product-detail/product-detail.component';
import { RouterModule } from '@angular/router';
import { NavComponent } from './public/nav/nav.component';
import { ProductGenreComponent } from './pages/product-genre/product-genre.component';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { FooterComponent } from './public/footer/footer.component';
import { SearchComponent } from './pages/search/search.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { ResultSearchComponent } from './pages/resultSearch/resultSearch.component';

@NgModule({
  declarations: [
    AppComponent,
    ProductListComponent,
    ShoppingCartComponent,
    ProductDetailComponent,
    NavComponent,
    ProductGenreComponent,
    HomeComponent,
    LoginComponent,
    ProfileComponent,
    RegistrationComponent,
    FooterComponent,
    SearchComponent,
    ResultSearchComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule.forRoot([
      {path: 'home', component: HomeComponent},
      {path: 'product/:id', component: ProductDetailComponent},
      {path: 'products', component: ProductListComponent},
      {path: 'genre/:name', component: ProductGenreComponent},
      {path: 'cart', component: ShoppingCartComponent},
      {path: 'profile', component: ProfileComponent},
      {path: 'login', component: LoginComponent},
      { path: 'registration', component: RegistrationComponent },
      {path: 'search/:encodedValue', component:ResultSearchComponent },
      {path: '', component: HomeComponent}
    ]),
    ReactiveFormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
