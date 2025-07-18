
import getopt, sys, urllib, time

def main():

    status = 0

# input arguments

    try:
        opts, args = getopt.getopt(sys.argv[1:],"h:iq",
                    ["help","invid=","quarter="])
    except getopt.GetoptError:

        tree = False
        for o, a in opts:
            if o in ("-h", "--help"):
                pass
            if o in ("-i", "--invid"):
                invid = str(a)
            if o in ("-q", "--quarter"):
                quarter = int(a)

    kepid, invid, kepmag, mode, start, stop, release = GetMetaData(invid,quarter)

# convert Gregorian date to Julian date

def Greg2JD(year, month, day):

    if (month < 3):
        y = float(year) - 1.0
        m = float(month) + 12.0
    else:
        y = float(year)
        m = float(month)
    a = 0; b = 0
    if (y + m / 12 + float(day) / 365 > 1582.87166):
        a = int(y / 100)
        b = 2 - a + int(float(a / 4))
    c = 0
    if (y < 0.0):
        c = int(365.25 * y - 0.75)
    else:
        c = int(365.25 * y)
    d = int(30.6001 * (m + 1))
    jd = float(b + c + d + day + 1720994.5);

    return jd

# start and stop Julian dates for Kepler quarters

def QuarterDates(quarter):

    Qstart = [2454953.5,2454964.5,2454998.5]
    Qstop  = [2454962.5,2454997.5,2455100.5]
    if (quarter < len(Qstart)):
        return Qstart[quarter] - 10, Qstop[quarter] + 10
    else:
        message  = 'No spacecraft roll dates recorded for quarter ' + str(quarter) + '.\n'
        message += 'Find an updated script at http://keplergo.arc.nasa.gov'
        sys.exit(message)

def GetMetaData(invid,quarter):

# get start and stop dates for quarter

    Qstart, Qstop = QuarterDates(quarter)

# URL for MAST data access

    url = 'http://archive.stsci.edu/kepler/data_search/search.php?'
    url += 'action=Search'
    url += '&max_records=100000'
    url += '&verb=3'
    url += '&ktc_investigation_id=' + invid
    url += '&ktc_target_type[]=LC'
    url += '&ktc_target_type[]=SC'
    url += '&outputformat=CSV'

# retrieve results from MAST

    lines = urllib.urlopen(url)

# extract metadata from CSV

    kepid = []; invid = []; mode = []
    ra = []; dec = []; kepmag = []
    start = []; stop = []; release = []
    for line in lines:
        line = line.strip().split(',')
        if (len(line[0]) > 0 and 
            'Kepler' not in line[0] and 
            'integer' not in line[0] and
            'no rows found' not in line[0]):
            GregStart = line[7][:10].split('-')
            GregStop = line[8][:10].split('-')
            JDstart = Greg2JD(int(GregStart[0]),int(GregStart[1]),int(GregStart[2]))
            JDstop = Greg2JD(int(GregStop[0]),int(GregStop[1]),int(GregStop[2]))
            if (JDstart > Qstart and JDstop < Qstop):
                kepid.append(line[0])
                invid.append(line[1])
                kepmag.append(float(line[22]))
                mode.append(line[6])
                ra.append(line[4])
                dec.append(line[5])
                start.append(line[7])
                stop.append(line[8])
                release.append(line[9])

    return kepid, invid, kepmag, mode, start, stop, release


#-------------------------------
if __name__ == "__main__":
  main()
  