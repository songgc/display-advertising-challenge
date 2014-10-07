'''
@author: Guocong Song
'''
import heapq
import sys

heapCap = int(sys.argv[1])
heap = []
for line in sys.stdin:
    key = hash(line)
    if len(heap) < heapCap:
        heapq.heappush(heap, (key, line))
    else:
        _, out = heapq.heappushpop(heap, (key, line))
        sys.stdout.write(out)

while len(heap) > 0:
    _, out = heapq.heappop(heap)
    sys.stdout.write(out)
        