import {Directive, HostBinding, HostListener} from '@angular/core';

@Directive({
  selector: '[appDropdown]'
})
export class DropdownDirective {

  show: boolean = false
  classForShown: string = '"btn-group open"'
  classForHidden: string = '"btn-group"'

  @HostBinding('class.open') isOpen = false

  @HostListener('click') toggleOpen() {
    this.isOpen = !this.isOpen;
  }
}
