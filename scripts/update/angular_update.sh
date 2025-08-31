#!/usr/bin/env bash
npx ng update @angular/core@latest @angular/cli@latest --force && npx npm-check-updates -u && npm install
