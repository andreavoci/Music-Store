import { ProductListComponent } from './pages/product-list/product-list.component';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { Routes, RouterModule } from '@angular/router';
import { ProductDetailComponent } from './pages/product-detail/product-detail.component';
import { ProductGenreComponent } from './pages/product-genre/product-genre.component';
import { ShoppingCartComponent } from './pages/shopping-cart/shopping-cart.component';
import { HomeComponent } from './pages/home/home.component';
import { ProfileComponent } from './pages/profile/profile.component';
import { LoginComponent } from './pages/login/login.component';
import { RegistrationComponent } from './pages/registration/registration.component';
import { ResultSearchComponent } from './pages/resultSearch/resultSearch.component';

const appRoutes: Routes =[
  {path: 'home', component: HomeComponent},
  {path: 'product/:id', component: ProductDetailComponent},
  {path: 'products', component: ProductListComponent},
  {path: 'genre/:name', component: ProductGenreComponent},
  {path: 'cart', component: ShoppingCartComponent},
  { path: 'profile', component: ProfileComponent },
  { path: 'login', component: LoginComponent },
  { path: 'registration', component: RegistrationComponent },
  { path: 'search/:encodedValue', component: ResultSearchComponent },
  { path: '', component: HomeComponent }
]

@NgModule({
  declarations: [],
  imports: [
    RouterModule.forRoot(appRoutes)
  ],
  exports: [RouterModule]
})
export class AppRoutingModule { }
