from collections import defaultdict
from math import prod
import requests as r
import re

header = {"Cookie":"session=53616c7465645f5f2e061303e05b2433e6962e62df700fd2db301698f01dcc8176ee2927772b555fec65f13f4e7fb3234afb5e545b8ad1530ebc880ddf16becb"}
response = r.request("GET",url="https://adventofcode.com/2023/day/2/input",headers=header)

# Part 1

limit = {
    "red":12,
    "green":13,
    "blue":14
}

pattern = r"((\d+)\s(\w+))"
sumi = 0

for line in response.text.splitlines():
    for ball_info in re.findall(pattern=pattern,string=line):
        if limit[ball_info[2]] < int(ball_info[1]): break
    else:
        sumi += int(re.findall(r"Game (\d+):",line)[0])

print("Part1: "+str(sumi))

# Part 2

power = 0

for line in response.text.splitlines():
    ball_set = defaultdict(int)
    for ball_info in re.findall(pattern=pattern,string=line):
        ball_set[ball_info[2]] = max(ball_set[ball_info[2]],int(ball_info[1]))
    power += prod(ball_set.values())

print("Part2: "+str(power))
