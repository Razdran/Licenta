import { TestBed } from '@angular/core/testing';

import { ScrappingServiceService } from './scrapping-service.service';

describe('ScrappingServiceService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ScrappingServiceService = TestBed.get(ScrappingServiceService);
    expect(service).toBeTruthy();
  });
});
