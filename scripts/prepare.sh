#!/usr/bin/env bash
set -e

ROOT="$( cd "$( dirname "${BASH_SOURCE[0]}" )"; cd ..; pwd )"

pushd $ROOT
VERSION=$(node -e "console.log(require('./package.json').version)")
node ./scripts/update-plugin-xml.js $VERSION
git commit -a -m "automatically bump plugin to v$VERSION"
popd
