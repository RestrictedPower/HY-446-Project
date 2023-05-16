import csv
import matplotlib.pyplot as plt

OUR_RESULTS = 'our_results.csv'
OTHER_RESULTS = 'other_results.csv'


def parseFile(filename):
    groupData = {}
    with open(filename, newline='') as csvfile:
        spamreader = csv.reader(csvfile, delimiter=' ', quotechar='|')
        for row in spamreader:
            print(row[0])
            minVal = min(row[0].split(";")[1:])
            testName = row[0].split(";")[0]
            namePrefix, size = testName.split("_")
            size = int(size[:-1])
            if namePrefix not in groupData:
                groupData[namePrefix] = {}
            groupData[namePrefix][size] = minVal
    return groupData


def plot(data, title, xLabel, yLabel):
    x1, x2, y1, y2 = [], [], [], []
    for i in data:
        x1.append(i[0])
        y1.append(i[1])
        if len(i) > 2:
            x2.append(i[0])
            y2.append(i[2])
    plt.figure()
    plt.plot(x1, y1, linewidth=2, marker='.')
    plt.plot(x2, y2, linewidth=2, marker='.')
    plt.xlabel(xLabel)
    plt.ylabel(yLabel)
    plt.title(title)
    return plt


OUR_DATA = parseFile(OUR_RESULTS)
OTHER_DATA = parseFile(OTHER_RESULTS)

for group in OUR_DATA:
    testName = group
    res = []
    for dataSize in OUR_DATA[group]:
        ourTime = int(OUR_DATA[group][dataSize])
        otherTime = 0
        if dataSize in OTHER_DATA[group]:
            otherTime = int(OTHER_DATA[group][dataSize])
        res.append([dataSize, ourTime, otherTime])
    res.sort()
    print(res)
    title = testName + " results"
    plt = plot(res, title, "Data Size", 'Execution time (ms)')
    fileName = testName + ".png"
    plt.savefig(fileName)
