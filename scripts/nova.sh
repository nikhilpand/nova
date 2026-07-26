#!/bin/bash
# NOVA Development Utility Scripts
# Run from project root

echo "=== NOVA Build & Test Suite ==="

case "$1" in
  build)
    echo "Building debug APK..."
    ./gradlew assembleDebug
    ;;
  test)
    echo "Running unit tests..."
    ./gradlew testDebugUnitTest
    ;;
  lint)
    echo "Running lint checks..."
    ./gradlew lint --continue
    ;;
  benchmark)
    echo "Running macrobenchmarks..."
    ./gradlew :benchmark:connectedCheck
    ;;
  clean)
    echo "Cleaning build artifacts..."
    ./gradlew clean
    ;;
  all)
    echo "Full pipeline: Clean → Build → Lint → Test"
    ./gradlew clean lint testDebugUnitTest assembleDebug
    ;;
  *)
    echo "Usage: ./scripts/nova.sh {build|test|lint|benchmark|clean|all}"
    ;;
esac
