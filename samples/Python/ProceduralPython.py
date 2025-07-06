import pandas as pd
import matplotlib.pyplot as plt
import numpy as np


def readTxt(file):
    data = []
    with open(file, "r") as f:
        for line in f:
            data.append(line.rstrip().split(' '))
    return data

def orders_on_day(data, day):
    counter = 0
    for row in data:
        if row[0] == day:
            counter += 1
    return counter
    
def days_without_order(data, city):
    counter = 0
    days = []
    for row in data:
        if row[1] == city:
            if row[0] not in days:
                days.append(row[0])
                counter += 1
    #print(counter, len(days))
    return 30 - len(days)
    
def max_amount(data):
    amounts = []
    for row in data:
        amounts.append(row[2])
    max_order = max(amounts)
    
    max_day = 0
    for row in data:
        if row[2] == str(max_order):
            max_day = row[0]
            
    return max_order, max_day

def orders_on_day_per_city(data, day, city):
    summ = 0
    for row in data:
        if int(row[0]) == day and row[1] == city:
            summ += int(row[2])
    return summ

def count_orders_per_city(data, days, city):
    counter = 0
    for row in data:
        if int(row[0]) in days and row[1] == city:
            counter += 1
    return counter

def sum_up_orders(data, day):
    summ = 0
    for row in data:
        if int(row[0]) == day:
            summ += int(row[2])
    return summ

def sum_up_orders_per_city(data, city):
    days = range(1, 31)
    orders = []
    for day in days:
        summ = 0
        for row in data:
            if row[1] == city and int(row[0]) == day:
                summ += int(row[2])
        orders.append(summ)
    return orders


def main():
    data = readTxt("order.txt")
    #print(data)
    
    #Task 2:
    print(f"Number of orders: {len(data)}")
    
    #Task 3:
    day = input("Give a day: ")
    print(f"Number of orders made on the {day}. day: {orders_on_day(data, day)}")
    
    #Task 4:
    city = "NR"
    number = days_without_order(data, city)
    if number == 0:
        print(f"There was order from {city} every day")
    else:
        print(f"There was {number} days when no order came from {city}")
    
    #Task 5:
    maximum_order, day_of_max_order = max_amount(data)
    print(f"Maximum order: {maximum_order}, first day: {day_of_max_order}")

    #Task 6
    #Task 7
    cities = ['PL', 'TV', 'NR']
    result = []
    day = 21
    for city in cities:
        result.append(orders_on_day_per_city(data, day, city))
    print(f"Number of orders placed on day {day} from {cities[0]}:{result[0]}, {cities[1]}: {result[1]}, {cities[2]}:{result[2]}")
    
    #Task8
    intervals = [range(1,11), range(10,21), range(20,31)]
    table = [["Days", "1..10", "10..20", "20..30"]]
    for city in cities:
        tablerow = []
        tablerow.append(city)
        for days in intervals:
            tablerow.append(count_orders_per_city(data, days, city))
        table.append(tablerow)
    
    #display table
    for row in range(len(table)):
        for item in table[row]:
            print(f"{item}", end="\t")
        print()
    
    #save to file
    f = open("table.txt", "w")
    for row in range(len(table)):
        line = ""
        for item in table[row]:
            line += str(item) + "\t"
        f.write(line + "\n")
    f.close()
    
    #Task 9
    x = ["1..10", "11..20", "21..30"]
    y1 = table[1][1:]
    y2 = table[2][1:]
    y3 = table[3][1:]
    plt.bar(x, y1, color="blue")
    plt.bar(x, y2, bottom=y1, color="green")
    plt.bar(x, y3, bottom=np.add(y1, y2), color="red")
    plt.xlabel("10-days period")
    plt.ylabel("Number of orders")
    plt.legend(cities)
    plt.title("Effect of advertisements")
    plt.show()
    
    #Task 10
    date_range = range(1, 31)
    values = []
    for day in date_range:
        values.append(sum_up_orders(data, day))
    data_dict = {'date_column': date_range, 'value_column': values}
    df = pd.DataFrame(data_dict)
    plt.plot(df['date_column'], df['value_column'], linestyle='dotted')
    plt.title("Time series of orders")
    plt.xlabel('Days')
    plt.ylabel('Number of orders')
    plt.savefig('timeseries.png')
    plt.show()
    
    #Task 11
    orders_PL = sum_up_orders_per_city(data, "PL")
    orders_TV = sum_up_orders_per_city(data, "TV")
    orders_NR = sum_up_orders_per_city(data, "NR")
    print(orders_PL, orders_TV, orders_NR, sep="\n")
    df = pd.DataFrame({"Day":date_range, "PL":orders_PL, "TV":orders_TV, "NR":orders_NR})
    df.plot(x="Day", y=["PL", "TV", "NR"], xlabel="Days", ylabel="Number of orders", title="Time series of orders per city")
    plt.show()


##################################################################
# Driver code
if __name__ == "__main__":
    main()