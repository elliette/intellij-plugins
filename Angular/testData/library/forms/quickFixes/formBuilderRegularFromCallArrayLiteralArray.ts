import {Component, inject} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';

@Component({
   selector: 'app-disabled-form-control',
   template: ``,
   standalone: false
})
export class DisabledFormControlComponent {
  private formBuilder = inject(FormBuilder);

  form = this.formBuilder.group(
    {
      name: this.formBuilder.group({
         first: ['Nancy', Validators.minLength(2)],
         last: 'foo',
      }),
      email: '',
    },
    {updateOn: 'change'},
  )

  check() {
    this.form.get(["name", "<warning descr="Unrecognized Angular Form control, array or group name">check</warning>"])
  }
}