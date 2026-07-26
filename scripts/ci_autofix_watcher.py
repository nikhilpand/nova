import time
import json
import urllib.request
import sys

REPO = "nikhilpand/nova"
URL = f"https://api.github.com/repos/{REPO}/actions/runs?per_page=1"
HEADERS = {"User-Agent": "CI-Watcher-Agent", "Accept": "application/vnd.github.v3+json"}

def get_latest_run():
    req = urllib.request.Request(URL, headers=HEADERS)
    try:
        with urllib.request.urlopen(req) as resp:
            data = json.loads(resp.read().decode('utf-8'))
            runs = data.get("workflow_runs", [])
            if runs:
                return runs[0]
    except Exception as e:
        print(f"Error fetching runs: {e}")
    return None

def fetch_run_jobs(jobs_url):
    req = urllib.request.Request(jobs_url, headers=HEADERS)
    try:
        with urllib.request.urlopen(req) as resp:
            data = json.loads(resp.read().decode('utf-8'))
            return data.get("jobs", [])
    except Exception as e:
        print(f"Error fetching jobs: {e}")
    return []

def main():
    print("[CI Tracker] Starting NOVA CI Automated Workflow Tracker...")
    while True:
        run = get_latest_run()
        if not run:
            print("[CI Tracker] No workflow runs found. Waiting...")
            time.sleep(10)
            continue

        run_id = run["id"]
        status = run["status"]
        conclusion = run.get("conclusion")
        html_url = run["html_url"]
        head_commit = run.get("head_commit", {}).get("message", "").split("\n")[0]

        print(f"[CI Tracker] Run #{run_id} | Status: {status} | Conclusion: {conclusion}")
        print(f"   Commit: '{head_commit}'")
        print(f"   URL: {html_url}")

        if status in ["in_progress", "queued"]:
            print("[CI Tracker] CI is currently running. Waiting 15 seconds...")
            time.sleep(15)
            continue

        if conclusion == "success":
            print("\n===========================================")
            print("SUCCESS: All CI checks and Gradle builds PASSED!")
            print("===========================================\n")
            sys.exit(0)
        elif conclusion == "failure":
            print("\n===========================================")
            print("FAILURE DETECTED in CI Run!")
            print("Fetching job logs for error analysis...")
            print("===========================================\n")

            jobs = fetch_run_jobs(run["jobs_url"])
            for job in jobs:
                if job["conclusion"] == "failure":
                    print(f"Job Name: {job['name']}")
                    for step in job.get("steps", []):
                        if step.get("conclusion") == "failure":
                            print(f"  FAILED Step: {step['name']}")
            sys.exit(1)
        else:
            print(f"Run completed with status: {conclusion}")
            sys.exit(2)

if __name__ == "__main__":
    main()
