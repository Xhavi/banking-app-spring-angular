import { TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';

describe('AppComponent', () => {
  it('renders the sidebar menu', () => {
    TestBed.configureTestingModule({
      imports: [AppComponent, RouterTestingModule]
    });

    const fixture = TestBed.createComponent(AppComponent);
    fixture.detectChanges();
    const compiled = fixture.nativeElement as HTMLElement;

    expect(compiled.querySelector('.sidebar')).not.toBeNull();
    expect(compiled.textContent).toContain('Clientes');
    expect(compiled.textContent).toContain('BANCO');
  });
});
