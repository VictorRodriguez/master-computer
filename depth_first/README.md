depth_first
===========

Depth_first algorithm

Search Algorithm #1
SEARCH#1
1.If GOAL?(initial-state) then return initial-state
2.INSERT(initial-node,FRINGE)
3.Repeat:
3.1.If empty(FRINGE) then return failure
  3.2.N <-- REMOVE(FRINGE)
  3.3.s <-- STATE(N)
  3.4.For every state s’ in SUCCESSORS(s)
    3.4.1.Create a new node N’ as a child of N
    3.4.2.If GOAL?(s’) then return path or goal state
    3.4.3.INSERT(N’,FRINGE)
