import csv
import pandas as pd
pd.set_option('display.max_colwidth', None)
pd.set_option('display.max_rows', None)
col_list = ["title", "abstract", "publish_time", "authors", "url"]
df = pd.read_csv('metadata.csv', usecols=col_list, encoding='utf8')    # , chunksize=1000)

t = df.sample(frac=0.0085, replace=True, random_state=1)
with open('tests/files.csv', 'w', encoding='utf-8', newline='') as out_file:
    writer = csv.writer(out_file)
    writer.writerow(('title', 'abstract', 'publish_time', 'authors', 'url'))

for index, row in t.iterrows():
    if str(row['title']) != "nan" and str(row['abstract']) != "nan" and str(row['publish_time']) != "nan" and str(row['authors']) != "nan" and str(row['url']) != "nan":
        lines = str(row['title']), str(row['abstract']), str(row['publish_time']), str(row['authors']), str(row['url'].split(';')[0])
        stripped = (line.strip() for line in lines)
        with open('tests/files.csv', 'a', encoding='utf-8', newline='') as out_file:
            writer = csv.writer(out_file)
            writer.writerow(stripped)
