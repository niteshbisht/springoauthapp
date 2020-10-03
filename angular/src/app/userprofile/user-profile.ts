import { Component, OnInit } from '@angular/core';

@Component({
    moduleId:module.id,
    selector: "profile-view",
    templateUrl:"user-profile.html",
    styleUrls:["user-profile.css"]
})

export class UsrProfileComponent implements OnInit {
        
        imageurl:string;
        username:string;
        contactNo:string;
        email:string;
        city:string;
        state:string;
        country:string;
        
        ngOnInit(): void {
           this.imageurl="./img/logo.png";
           this.username="nitesb";
           this.contactNo="9789880313";
           this.email="niteshbisht@yahoo.com";
           this.city="Dehardun";
           this.state="Uttarakhand";
           this.country="India";
        }


}