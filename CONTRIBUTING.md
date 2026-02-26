# Contributing to 3P

We love your input! We want to make contributing to this project as easy and transparent as possible.

## Branching Strategy

We follow a standard Git Flow branching strategy:

- **`main`**: Production-ready code. All code here must be stable and tested.
- **`develop`**: The main development branch. Features are merged here before going to `main`.
- **`feature/<feature-name>`**: Created from `develop`. Used for new features.
- **`bugfix/<bug-name>`**: Created from `develop`. Used for fixing bugs.
- **`release/vX.Y.Z`**: Created from `develop` when preparing for a new release.

## Commit Messages

Always comment on the commits with the feature or bug you are working on (good practices).
Format: `type: description` (e.g., `feat: add home screen`, `fix: resolve navigation crash`).
