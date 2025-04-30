import os
import re
import time

import requests
from bs4 import BeautifulSoup

BASE_URL = "https://fallout.fandom.com"
PERK_LIST_URL = f"{BASE_URL}/wiki/Fallout_perks"
HEADERS = {"User-Agent": "Mozilla/5.0"}
OUTPUT_DIR = "perk_images"
os.makedirs(OUTPUT_DIR, exist_ok=True)


def slugify(name):
    return re.sub(r'[^a-zA-Z0-9]+', '_', name).strip('_')


def get_perk_links():
    res = requests.get(PERK_LIST_URL, headers=HEADERS)
    soup = BeautifulSoup(res.text, "html.parser")
    perks = set()

    for table in soup.select("table.va-table"):
        for row in table.select("tr")[1:]:  # skip header
            name_cell = row.find("td")
            if not name_cell:
                continue
            a = name_cell.find("a", href=True, title=True)
            if a:
                title = a["title"]
                link = BASE_URL + a["href"]
                perks.add((title, link))
    return sorted(perks)


def download_infobox_image(perk_name, url):
    try:
        print(f"🔍 {perk_name}")
        res = requests.get(url, headers=HEADERS)
        soup = BeautifulSoup(res.text, "html.parser")

        infobox = soup.find("aside", class_="portable-infobox")
        if not infobox:
            print(f"❌ Infobox manquante : {perk_name}")
            return

        img = infobox.find("img")
        if not img or not img.get("src"):
            print(f"❌ Image non trouvée : {perk_name}")
            return

        img_url = img["src"]
        img_data = requests.get(img_url, headers=HEADERS).content
        filename = os.path.join(OUTPUT_DIR, f"{slugify(perk_name)}.webp")

        with open(filename, "wb") as f:
            f.write(img_data)

        print(f"✅ {perk_name} → {filename}")
        time.sleep(1)

    except Exception as e:
        print(f"⚠️ Erreur pour {perk_name}: {e}")


def main():
    perks = get_perk_links()
    print(f"🎯 {len(perks)} perks trouvés.")
    for name, url in perks:
        download_infobox_image(name, url)


if __name__ == "__main__":
    main()
