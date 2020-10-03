import { Component } from '@angular/core';
import {
	Http,
	Response,
	Headers,
	RequestOptions
} from '@angular/http';
import { CanActivate, Router, RouterStateSnapshot } from '@angular/router';
import 'rxjs/add/operator/toPromise';
import { Cookie } from 'ng2-cookies';
var headers = new Headers();
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'app works!';
  cookies: Object;
  keys: Array<string>;
  authcookie:string;
  sentData:string;
  
  constructor(private http: Http,private router:Router) {
    this.update();
    console.log(this.cookies);
  }
  update() {
    this.cookies = Cookie.getAll();
    this.keys = Object.keys(this.cookies);
	  this.authcookie = this.cookies['AUTH-TOKEN'];
	if(this.cookies!=null){
    headers.set('X-AUTH-TOKEN',this.authcookie);
    this.getCustomData();
    // this.router.navigateByUrl('/usrprofile');
	}
	console.log(headers);
  }
  getData(url:string):Promise<boolean>{
	let options = new RequestOptions({
					headers : headers
				});
	return this.http.get(url, options).toPromise().then(this.extractData);
  }
  private extractData(res : Response) {
    let bb = res;
    console.log(bb);
    let body = res.json();
		return body.data || {};
  }
  getCustomData():void{
    let url='http://localhost:8080/statelesssocial/api/facebook/details';
    this.getData(url).then(
      sentData =>{
        this.addElementCountry('a');
      }
    )
  }
  addElementCountry(sentData:string):void{
    this.sentData = sentData;
  }
}
