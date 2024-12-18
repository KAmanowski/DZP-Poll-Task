import React from 'react';
import { render, screen } from '@testing-library/react';
import Index from '../index';

test('renders logo', () => {
  render(<Index />);
  const altElement = screen.getAllByAltText('logo')
  expect(altElement).toBeInTheDocument();
});
