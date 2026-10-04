# Security policy

## Supported code

Security fixes are applied to the current default branch. Consumers should use
the latest applicable GuicedEE service release and review the upstream library's
security advisories, including dependencies bundled in shaded artifacts.

## Reporting a vulnerability

Use [GitHub's private vulnerability reporting](https://github.com/GuicedEE/Services/security/advisories/new)
to report suspected vulnerabilities. Include the affected service and version,
reproduction steps, impact, and a suggested fix if available. Keep exploit
details and credentials out of public issues and pull requests.

## Workflow dependencies

This repository's external GitHub Actions and reusable workflows are pinned to full commit hashes.
Dependabot proposes updates to those pins for review. Publishing jobs use the
permissions required by the shared build workflow; change detection uses a
read-only token and does not persist checkout credentials.
