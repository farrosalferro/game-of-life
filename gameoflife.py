import time

filename = input("input initial state file: \n")
# iteration = int(input("number of iteration: "))
f = open(filename, 'r')
# print(len(f.readlines()))

def inputToMatrix(file):
    matrix = []
    life_coordinates = []
    for i, line in enumerate(file.readlines()):
        row = []
        for j, element in enumerate(line):
            if element == '.':
                row.append(0)
            elif element == '*':
                row.append(1)
                life_coordinates.append([i, j])
            else:
                continue
        matrix.append(row)
    
    return matrix

# print(inputToMatrix(f))

def permutation(xs, ys):
    p = []
    for x in xs:
        for y in ys:
            if x == 0 and y == 0:
                continue
            p.append([x, y])

    return p
    
# print(permutation([-1, 0, 1], [-1, 0, 1]))

def nextState(prevState):
    next_state = []
    for i, row in enumerate(prevState):
        new_row = []
        for j, element in enumerate(row):
            count = 0
            # condition y
            if i == 0:
                xs = [0, 1]
            elif i == len(prevState)-1:
                xs = [0, -1]
            else:
                xs = [-1, 0, 1]

            # condition x
            if j == 0:
                ys = [0, 1]
            elif j == len(row)-1:
                ys = [0, -1]
            else:
                ys = [-1, 0, 1]

            adj_coors = permutation(xs, ys)

            for adj_coor in adj_coors:
                x = j + adj_coor[1]
                y = i + adj_coor[0]
                if prevState[y][x] == 1:
                    count += 1

            if count < 2 or count > 3:
                # prevState[i][j] = 0
                new_row.append(0)
            elif count == 3:
                # prevState[i][j] = 1
                new_row.append(1)
            else:
                new_row.append(prevState[i][j])
        next_state.append(new_row)

    # return prevState
    return next_state

def matrixToInput(matrix):
    output = ''
    for row in matrix:
        for element in row:
            if element == 0:
                output = output + '.'
            else:
                output = output + '*'
        output = output + '\n'

    return output

    
prevState = inputToMatrix(f)
# for _ in range(iteration):
while True:
    next_state = nextState(prevState)
    print(matrixToInput(next_state))
    print('--------------------')
    prevState = next_state
    time.sleep(0.25)
# print(next_state)






            



