class MatrixIndexError(Exception):
    '''An attempt has been made to access an invalid index in this matrix'''


class MatrixDimensionError(Exception):
    '''An attempt has been made to perform an operation on this matrix which
    is not valid given its dimensions'''


class MatrixInvalidOperationError(Exception):
    '''An attempt was made to perform an operation on this matrix which is
    not valid given its type'''


class MatrixNode():
    '''A general node class for a matrix'''

    def __init__(self, contents, right=None, down=None):
        '''(MatrixNode, obj, MatrixNode, MatrixNode) -> NoneType
        Create a new node holding contents, that is linked to right
        and down in a matrix
        '''
        # REPRESENTATION INVARIANT
        # _contents is an object containing the main element of the MatrixNode
        # _right is a horizontal pointer that points to either None or a
        # MatrixNode
        # _down is a vertical pointer that points to either None or a
        # MatrixNode
        # _row_index indicates the horizontal index within a row
        # _col_index indicates the vertical index within a column

        self._contents = contents
        self._right = right
        self._down = down
        # attribute a row index
        self._row_index = -1
        # attribute a column index
        self._col_index = -1

    def __str__(self):
        '''(MatrixNode) -> str
        Return the string representation of this node
        '''
        return str(self._contents)

    def is_equal(self, second_node):
        '''
        (MatrixNode, MatrixNode or None) -> bool

        Determines if two nodes are equal.
        '''
        if second_node is None:
            ret = False
        else:
            # check if the contents are the same
            same_contents = self._contents == second_node.get_contents()
            # check if the row indexes are the same
            same_row_index = self._row_index == second_node.get_row_index()
            # check if the column indexes are the same
            same_col_index = self._col_index == second_node.get_col_index()
            # if all three items are the same, then it is considered equal
            ret = same_contents and same_row_index and same_col_index
        return ret

    def get_contents(self):
        '''(MatrixNode) -> obj
        Return the contents of this node
        '''
        # just return the contents
        return self._contents

    def set_contents(self, new_contents):
        '''(MatrixNode, obj) -> NoneType
        Set the contents of this node to new_contents
        '''
        # set the contents
        self._contents = new_contents

    def get_right(self):
        '''(MatrixNode) -> MatrixNode
        Return the node to the right of this one
        '''
        # return the right node
        return self._right

    def set_right(self, new_node):
        '''(MatrixNode, MatrixNode) -> NoneType
        Set the new_node to be to the right of this one in the matrix
        '''
        # set the right node to the new node
        self._right = new_node

    def get_down(self):
        '''(MatrixNode) -> MatrixNode
        Return the node below this one
        '''
        # return the downward node
        return self._down

    def set_down(self, new_node):
        '''(MatrixNode, MatrixNode) -> NoneType
        Set new_node to be below this one in the matrix
        '''
        # set the downward node to the new node
        self._down = new_node

    def get_col_index(self):
        '''
        (MatrixNode) -> int
        Returns the column index within the matrix
        '''
        # just return the downward index
        return self._col_index

    def set_col_index(self, index):
        '''
        (MatrixNode, int) -> None
        Sets the column index within the matrix
        '''
        # just set the downward index to the given integer
        self._col_index = index

    def get_row_index(self):
        '''
        (MatrixNode) -> int
        Returns the row index within the matrix
        '''
        # return the row index
        return self._row_index

    def set_row_index(self, index):
        '''
        (MatrixNode, int) -> None
        sets the row index within the matrix
        '''
        # set the row index as given
        self._row_index = index


class Matrix():
    '''A class to represent a mathematical matrix
    Note: Uses 0-indexing, so an m x n matrix will have
    indices (0,0) through (m-1, n-1)'''

    def __init__(self, m, n, default=0):
        '''(Matrix, int, int, float) -> NoneType

        Create a new m x n matrix with all values set to default. Both m and n
        must be positive integers.

        RAISES MatrixDimensionError if m <= 0 or n <= 0;
        both m and n must be positive integers
        '''
        # REPRESENTATION INVARIANT
        # _head is a MatrixNode that links to all unique values of the matrix
        # _num_rows is a positive integer that indicates the number of
        # rows there are in the matrix
        # _num_cols is positive integer that indicates the number of columns
        # there are in the matrix
        # _default_value is a float that is a non-unique value within the
        # matrix
        #
        # the size of the matrix is _num_rows by _num_cols
        # if 0 > i or i >= _num_rows:
        #     (i, j) is an invalid index
        #
        # if 0 > j or j >= _num_cols:
        #     (i, j) is an invalid index
        #
        # if v is a value at (i, j):
        #     if v != _default_val:
        #         there exists a node, stemming from the _head
        #         that has a row index of i and a column index j
        #     otherwise:
        #         there doesn't exist a node that stems from the head
        #         with a row index of i and a column index of j
        #
        # if i > j:
        #     row_1 = _head.down[i], a MatrixNode
        #     row_2 = _head.down[j], a MatrixNode
        #     row_2's column index < row_1's column index
        #     row_2 vertically appears before row_1
        #
        #     col_1 = _head.right[i], a MatrixNode
        #     col_2 = _head.right[j], a MatrixNode
        #     col_2's row index < col_1's row index
        #     col_2 horizontally appears before col_1;
        #
        #     if m > n:
        #         col_1.down[n]'s row index < col_1.down[m]'s row index
        #         col_1.down[n] vertically appears before col_1.down[m]
        #         col_1.down[n]'s contents is an element within the matrix
        #         that is not _default_value
        #
        #         row_1.right[n]'s column index < row_1.right[m]'s column index
        #         row_1.right[n] horizontally appears before row_1.right[m]
        #         row_1.right[n]'s contents is an element within the matrix
        #         that is not _default_value
        #
        #         if i == m:
        #              row_1.right[m] is equal to col_1.down[m]; having the
        #              same indexes as well as its contents. They are not
        #              duplicates

        if m <= 0 or n <= 0:
            raise MatrixDimensionError('Please insert positive\
integers\nYour m input: ' + str(m) + '\nYour n input: ' + str(n))
        self._head = MatrixNode(None)
        self._num_rows = m
        self._num_cols = n
        self._default_value = default

    def __str__(self):
        '''
        (Matrix) -> str

        Returns a string representation of the matrix
        '''
        # create an output variable
        output = ''
        # we go through two for loops
        # i and j; i representing the rows, j representing the columns
        for i in range(self._num_rows):
            # add a line for the visual representation at the very beginning
            # of the row
            output += '|'
            for j in range(self._num_cols):
                # we get the value in the allocated index
                output += str(self.get_val(i, j)) + ' '
            # add another line for the visual representation at the end of the
            # row and remove the extra whitespace, and start off we a new line
            output = output[:-1] + '|\n'
        # then we just remove the new line
        output = output.strip()
        return output

    def get_size(self):
        '''
        (Matrix) -> int, int
        Returns the size of the matrix

        first integer returned is the # of rows
        seccond integer return is the number of columns
        '''
        return self._num_rows, self._num_cols

    def get_val(self, i, j):
        '''(Matrix, int, int) -> float
        Return the value of m[i,j] for this matrix m
        '''
        # first, check if i and j are valid rows and columns
        self.are_indexes_valid(i, j)
        # set a return value as the default value
        ret = self._default_value
        # check if row index exists in our matrix; this is just an allocated
        # row. we put i + 1 because we want the node before i + 1, which
        # is node i
        row = self._get_node_in_col_before_index(i + 1, self._head)
        # if the row's index is not i; it doesn't exist
        if row.get_row_index() == i:
            # we traverse horizontally; we put j + 1 because we want the
            # node before j + 1 which is node (i, j)
            at_col = self._get_node_in_row_before_index(j + 1, row)
            # at the column's index, check if the column is equal to j
            if at_col.get_col_index() == j:
                # if it is, then we can return our value
                ret = at_col.get_contents()

        return ret

    def set_val(self, i, j, new_val):
        '''(Matrix, int, int, float) -> NoneType
        Set the value of m[i,j] to new_val for this matrix m

        RAISES MatrixIndexError if the given row index is not in the range
        of the amount of rows there are in the matrix
        RAISES MatrixIndexError if the given column index is not in the range
        of the amount of columns there are in the matrix
        '''
        # first, we check if i and j are valid rows and columns
        self.are_indexes_valid(i, j)
        # if the new_value is just the default value we can just delete the
        # node
        if (new_val == self._default_value):
            self._remove_element(i, j)
            # note that the remove element function first checks if the
            # value is unique;
            # if the value at the (i, j)th node of the matrix doesn't exist:
            #     do nothing
            # else if the value at the (i, j)th node of the matrix does exist:
            #     'delete' the node from our matrix

        # otherwise
        else:
            # note that two helper functions do almost exactly what I need
            # them to do
            # EXPLANATION OF HELPER FUNCTION; add_verti/horiz_node
            #     if there doesn't exist a node in its respective row/col
            #     index (either horizontally or vertically):
            #         respectively add the node by its given row/col index
            #         within the matrix
            #     if there exists a node in its respective row/col index
            #     (either horizontally or vertically):
            #         just replace the contents of the node with the content
            #         given in its parameters
            #     return the node that we added/replaced

            # we get the 'row' to play with, so we get/create the node that
            # stems from the head downward to the ith position
            row = self._add_vertical_node(i, i, self._head)
            # at the row, we add the node with its contents horizontally,
            # indicated by the jth column
            new_element = self._add_horizontal_node(j, new_val, row)
            # similarly, we get the column to play with, so we get/create
            # the node that stems from the head rightward to the jth position
            col = self._add_horizontal_node(j, j, self._head)
            # note that if we use the helper function (a._add_vertical_node)
            # this would actually make a copy; two seperate nodes would
            # be linked vertically and horizontally. So it won't be able
            # to do a traversal such as down-> right -> down -> right

            # at the column, we want to vertically traverse downward and
            # we want the node before row index
            before = self._get_node_in_col_before_index(i, col)
            # get the node right below; we don't care if its None
            after = before.get_down()

            # we have to check if the new element and after are not the same
            # if they were and we link it, it would be an infinite LL
            # this accounts for the base case of "at the end" of the column
            if not (new_element.is_equal(after)):
                # set new_element's below to after
                new_element.set_down(after)

            # set before's down to the new element
            before.set_down(new_element)
            # set the row index and the column index
            new_element.set_row_index(i)
            new_element.set_col_index(j)

    def get_row(self, row_num):
        '''(Matrix, int) -> OneDimensionalMatrix

        Return the row_num'th row of this matrix

        RAISES MatrixIndexError if the given row_num is not in the range
        of the amount of rows there are in the matrix
        '''
        # first, check if the row num is valid within the number of rows there
        # are in the matrix
        self.are_indexes_valid(row_num, 0)
        # create the OneDimensionalMatrix; 1 x n
        ret_mat = OneDimensionalMatrix(self._num_cols, self._default_value)
        # we could use our set_val method, but it would be considered
        # inefficient due to the number of sub functions there are
        # so, instead, we traverse to the row that we want
        row = self._get_node_in_col_before_index(row_num + 1, self._head)
        # if the "row's" row index is the same as the row index passed
        #    there exists one or more 'unique' values in that row

        if row.get_row_index() == row_num:
            # using helper functions at this point would be inefficient
            # create a crawler
            # we assume that there is a node to the right of row; otherwise
            # it shouldn't be there because set_val has coverage on it
            crawler = row.get_right()
            while crawler is not None:
                # in this case, get the respective column index
                horizontal_index = crawler.get_col_index()
                # as well as its contents
                horizontal_contents = crawler.get_contents()
                # then by abstraction, we place the horizontal contents
                # to the respective index
                ret_mat.set_item(horizontal_index, horizontal_contents)
                # then we go further
                crawler = crawler.get_right()
        # then we return the matrix
        return ret_mat

    def set_row(self, row_num, new_row):
        '''(Matrix, int, OneDimensionalMatrix) -> NoneType
        Set the value of the row_num'th row of this matrix to those of new_row

        RAISES MatrixIndexError if the given row_num is not in the range
        of the amount of rows there are in the matrix

        RAISES MatrixDimensionError if the given OneDimensionalMatrix does
        not have the same amount of elements as there are rows in this matrix
        '''
        # first, check if the row num is valid within the number of rows there
        # are in the matrix
        self.are_indexes_valid(row_num, 0)
        # then check if the number of elements of the one dimensional matrix
        # is the same amount of rows in this matrix
        row_num_elements = new_row.get_num_elements()
        if row_num_elements != self._num_cols:
            raise MatrixDimensionError('\nThis matrix has ' +
                                       str(self._num_cols) + ' column(s)\n\
The matrix passed has ' + str(row_num_elements) + ' element(s)\n\
Please insert a OneDimensionalMatrix with ' + str(self._num_cols) +
                                            ' element(s)')

        # several ways of doing this; picking the most simplistic option
        for at_column in range(self._num_cols):
            # get the value from the passed row
            row_getter = new_row.get_item(at_column)
            # set the value to the row getter
            Matrix.set_val(self, row_num, at_column, row_getter)

    def get_col(self, col_num):
        '''(Matrix, int) -> OneDimensionalMatrix
        Return the col_num'th column of this matrix

        RAISES MatrixIndexError if the given row_num is not in the range
        of the amount of rows there are in the matrix
        '''
        # check if the column number passed is valid as to how many columns
        # there are within the matrix
        self.are_indexes_valid(0, col_num)
        # we create the OneDimensionalMatrix we want to return, n x 1
        ret_mat = OneDimensionalMatrix(self._num_rows, self._default_value,
                                       False)
        # we traverse rightward to the column we want, from the head
        col = self._get_node_in_row_before_index(col_num + 1, self._head)
        # if "col's" col index is equal to col_num:
        #    this is the column we want to return since there are unique
        #    values
        # otherwise:
        #    the column is just a column of default values
        if (col.get_col_index() == col_num):
            # using the set_val method would be inefficient since it traverses
            # from the head to the col every time we want to set a value
            # we create a crawler
            crawler = col.get_down()
            # we get the contents
            while crawler is not None:
                # get the crawler's index
                crawlin_index = crawler.get_row_index()
                # as well as its contents
                cont = crawler.get_contents()
                # then we just set the item, via OneDimensionalMatrix
                # abstraction
                ret_mat.set_item(crawlin_index, cont)
                # then crawler goes downward
                crawler = crawler.get_down()
        # then we return the OneDimensionalMatrix
        return ret_mat

    def set_col(self, col_num, new_col):
        '''(Matrix, int, OneDimensionalMatrix) -> NoneType

        Set the value of the col_num'th column of this matrix to those
        of the new_col

        RAISES MatrixIndexError if the given col_num is not in the range
        of the amount of columns there are in the matrix
        RAISES MatrixDimensionError if new_col does not have the
        same amount of elements as there are columns in this matrix
        '''
        # first check if the given col_num is valid within the number of
        # columns there are in that matrix
        self.are_indexes_valid(0, col_num)
        # then check if the number of elements of the one dimensional matrix
        # is the same amount of columns in this matrix
        col_num_elements = new_col.get_num_elements()
        if (col_num_elements != self._num_rows):
            raise MatrixDimensionError('\nThis matrix has ' +
                                       str(self._num_rows) + ' row(s)\nThe\
 matrix passed has ' + str(col_num_elements) + ' elements(s)\nPlease insert\
 a OneDimensionalMatrix with ' + str(self._num_rows) + ' element(s)')

        # several ways of doing this; picking the most simplistic option
        for at_row in range(self._num_rows):
            # get the value from the passed column
            col_getter = new_col.get_item(at_row)
            # set the value in the respective index to the col getter
            Matrix.set_val(self, at_row, col_num, col_getter)

    def swap_rows(self, i, j):
        '''(Matrix, int, int) -> NoneType
        Swap the values of rows i and j in this matrix

        RAISES MatrixIndexError if i or j is not in the range of the amount of
        rows there are in the matrix
        '''
        # check if both indexes are invalid
        self.are_indexes_valid(i, 0)
        self.are_indexes_valid(j, 0)
        # We will do this the most simple way possible

        # to reduce traversals,
        # if both i == j:
        #    we really don't need to 'swap' them
        if (i != j):
            # we get one row, as a OneDimensional Matrix, indicated by i
            primary_row = self.get_row(i)
            # then get another indicated by j
            secondary_row = self.get_row(j)
            # set the primary row to the matrix's row of j
            self.set_row(j, primary_row)
            # and we set the secondary row to matrix's row of i
            self.set_row(i, secondary_row)

    def swap_cols(self, i, j):
        '''(Matrix, int, int) -> NoneType
        Swap the values of columns i and j in this matrix

        RAISES MatrixIndexError if i or j is not in the range of the amount of
        rows there are in the matrix
        '''
        # check if both indexes are invalid
        self.are_indexes_valid(0, i)
        self.are_indexes_valid(0, j)
        # Again, we will do this the most simplest way possible
        # to reduce traversals,
        # if both i == j:
        #    we really don't need to 'swap' them

        if (i != j):
            # get one column, as a OneDimensionalMatrix, indicated by i
            primary_col = self.get_col(i)
            # get another indicated by j
            secondary_col = self.get_col(j)
            # set the primary column to the matrix's column of j
            self.set_col(j, primary_col)
            # set the secondary column to the matrix's column of i
            self.set_col(i, secondary_col)

    def add_scalar(self, add_value):
        '''(Matrix, float) -> NoneType
        Increase all values in this matrix by add_value
        '''
        # First we add the value to our default value
        self._default_value += add_value
        # Then we can do this one of two ways;
        # - keep on using get_val and set_val
        # OR
        # - we can reduce traversals by going to one row,
        #     traverse through the row, adding the contents by add_value
        #     then go to the next row

        # get the first row
        at_row = self._head.get_down()
        # eliminate the base case, no unique values
        while at_row is not None:
            # traverse through the row
            at_element = at_row.get_right()
            while at_element is not None:
                # get the contents, adding the value passed to this method
                cont = at_element.get_contents() + add_value
                # set the contents to the value passed with the unique value
                at_element.set_contents(cont)
                # go to the next element
                at_element = at_element.get_right()
            # go to the next row
            at_row = at_row.get_down()

    def subtract_scalar(self, sub_value):
        '''(Matrix, float) -> NoneType
        Decrease all values in this matrix by sub_value
        '''
        # no need to copy and paste code from add_scalar
        # we just set the sub_value negative since
        # x - y = x + (-y)
        sub_value *= -1
        self.add_scalar(sub_value)

    def multiply_scalar(self, mult_value):
        '''(Matrix, float) -> NoneType
        Multiply all values in this matrix by mult_value
        '''
        # we can't use add_scalar as it would be inefficient
        # but the idea is the same
        # we multiply our default value
        self._default_value *= mult_value

        # we will go through each row
        at_row = self._head.get_down()
        # eliminate the base case; no unique values
        while (at_row is not None):
            # traverse through the row
            at_element = at_row.get_right()
            while at_element is not None:
                # get the contents and multiply it by the mult value
                cont = at_element.get_contents() * mult_value
                # set the element's contents to the multiplied value
                at_element.set_contents(cont)
                # continue to traverse through the row
                at_element = at_element.get_right()
            # continue to traverse through rows
            at_row = at_row.get_down()

    def add_matrix(self, adder_matrix):

        '''(Matrix, Matrix) -> Matrix

        Return a new matrix that is the sum of this matrix and adder_matrix.
        The matrices must have the same size.

        RAISES MatrixDimensionError if both matrices don't have the same
        size
        '''
        if (self.get_size() != adder_matrix.get_size()):
            raise MatrixDimensionError('The Matrices are not the same size')
        # in this code/class, we know the implementation and details of the
        # class 'Matrix', so it is okay to call adder_matrix's default value

        # first we create our default value
        def_val = self._default_value + adder_matrix._default_value
        # then we create our matrix
        ret_mat = Matrix(self._num_rows, self._num_cols, def_val)

        # we go through all rows
        for i in range(self._num_rows):
            # and through all columns
            for j in range(self._num_cols):
                # we get our own matrix's contents as well as the adder
                # matrix's contents
                cont = self.get_val(i, j) + adder_matrix.get_val(i, j)
                # then just set the contents to the returning matrix
                ret_mat.set_val(i, j, cont)
        # return the returning matrix
        return ret_mat

    def multiply_matrix(self, mult_matrix):
        '''(Matrix, Matrix) -> Matrix

        Returns a n by m matrix that is a product of this matrix and
        mult_matrix

        The dimensions for this matrix must be n by r
        The dimensions for mult_matrix must be r by m

        RAISES MatrixDimensionError if the number of columns of this matrix
        is not equal to the number of rows of this matrix
        '''
        # first, we get sizes of the main matrix and mult_matrix
        # in this case, primary and secondary
        prim_num_rows, prim_num_cols = self.get_size()
        seco_num_rows, seco_num_cols = mult_matrix.get_size()

        # if the matrix is a one dimensional matrix we have to make
        # adjustments
        if (mult_matrix.__class__ == OneDimensionalMatrix):
            # we assume that the matrix passed is a 1 by n matrix
            # so we must check if this matrix has 1 column
            if (prim_num_cols != 1):
                # if it doesn't then we have to raise an error
                raise MatrixDimensionError('The number of columns of\
 this matrix\ is not equivalent to the number of rows of the passed matrix')

            # we get the number of elements within the mult matrix
            num_vals = max(seco_num_rows, seco_num_cols)
            # then create a matrix
            ret_mat = Matrix(prim_num_rows, num_vals)
            # we set up two loops
            # C (i, k) = A(i, 1) * B(1, k)
            for i in range(prim_num_rows):
                for k in range(num_vals):
                    # no summer is needed
                    summer = self.get_val(i, 0) * mult_matrix.get_item(k)
                    # just set the value
                    ret_mat.set_val(i, k, summer)
        else:
            # we check if the primary matrix's columns is equal to the number
            # of rows of the secondary matrix
            if (prim_num_cols != seco_num_rows):
                # we raise the error indicated in our docstring
                raise MatrixDimensionError('The number of columns of this\
 matrix is not equivalent to the number of rows of the passed matrix')
            ret_mat = Matrix(prim_num_rows, seco_num_cols)
            # C (i,k) = A(i,j) * B (j, k)

            # we create three loops
            for i in range(prim_num_rows):
                for k in range(seco_num_cols):
                    # create a summing variable
                    summer = 0
                    for j in range(prim_num_cols):
                        # we remember that prim_num_cols == secon_num_rows
                        prim_val = self.get_val(i, j)
                        secon_val = mult_matrix.get_val(j, k)
                        # modulated because of PEP8....
                        summer += prim_val * secon_val
                    ret_mat.set_val(i, k, summer)
        return ret_mat

    def _get_node_in_row_before_index(self, index, base_node):
        '''
        (Matrix, int, MatrixNode) -> MatrixNode

        Returns the node before the given index within a matrix row, given
        the base node.
        '''
        # index is assumed to be greater than base_node's column index
        # set up a crawler to traverse
        crawler = base_node
        # in this case, we can get the crawler's column index
        crawling_index = crawler.get_col_index()
        # set up a variable that tags right before the crawler
        # for now, we set it as the base node as it is a base case
        tagger = base_node

        # if the crawling index is greater or equal to the index
        #     This is an efficient stop, we found the node AND
        # if the crawler is none:
        #     the tagger should be the very last node; implying that
        #     index passed is greater than the last node's index in its row
        # Note: the second condition fufills the 1 node base case

        while index > crawling_index and crawler is not None:
            tagger = crawler
            crawler = crawler.get_right()
            if crawler is not None:
                crawling_index = crawler.get_col_index()
        return tagger

    def _get_node_in_col_before_index(self, index, base_node):
        '''
        (Matrix, int, MatrixNode) -> MatrixNode

        Returns the node before the given index within a matrix column, given
        the base node.
        '''
        # given that the method is private, we can assume that the index is
        # 'reasonable', (i.e. base_node's column index < index)
        # AND
        # the base node is a MatrixNode, not None

        # set up a crawler to traverse
        crawler = base_node
        # in this case, we can get the crawler's column index
        crawling_index = crawler.get_row_index()
        # set up a variable that tags right before the crawler
        # for now, we set it as the base node as it is a base case
        tagger = base_node

        # if the crawling index is greater or equal to the index
        #     This is an efficient stop, we found the node AND
        # if the crawler's down pointer is none;
        #     stop the loop, having a tagger and the crawler in the last
        #     two places of the column.
        # Note: the second condition fufills the 1 node base case

        while index > crawling_index and crawler is not None:
            tagger = crawler
            crawler = crawler.get_down()
            if crawler is not None:
                crawling_index = crawler.get_row_index()
        return tagger

    def _add_horizontal_node(self, horiz_index, content, base_node):
        '''
        (Matrix, int, float, MatrixNode) -> MatrixNode

        Adds and returns a MatrixNode, containing the content,
        horizontally to the matrix in the same row as the base node;
        placed in the horizontal index allocation.

        If there exists a node at the specified index of the row, this method
        will just replace its contents with the content given.
        '''
        # we assume that the content is not the default value
        # we also assume that base_node's row index < horiz_index
        # in this case, let's get the node before the horizontal index
        left_of = self._get_node_in_row_before_index(horiz_index, base_node)

        # check if the node to the right of 'left_of' has the index of
        # the given horizontal index; potentially at the node we want
        pot_node = left_of.get_right()

        # if there exists the potential node we want
        #     there is just a node to the right; nothing special about it
        exists = pot_node is not None
        # AND
        # if row index is the horizontal index passed
        #     this is the node we want to switch the contents so we don't
        #     inefficiently use memory

        # NOTE: if the first case fails (before the AND)
        #     then the second case is not ran, covering the base case of
        #     the potential node being None and not crashing

        if exists and (pot_node.get_col_index() == horiz_index):
            # we can just switch the contents
            pot_node.set_contents(content)
            # create a return
            ret = pot_node

        # otherwise
        # if the node, 'left_of', is at the end of the row
        #     the right of it points to None
        # if there exists a gap of row indexes; left_of's index to pot_node's
        # index
        #     we must put a new node in between

        else:
            # well, we already have two very important nodes that are
            # initialized
            #     'left_of' is the node where we want to point its right
            #     to the new node
            # AND
            #     'pot_node' is either a node or None that the new node
            #     must point to (since it is to the right of before)
            # below covers two base cases: singular node and end of row

            # We create a node and implement the second condition
            middle = MatrixNode(content, pot_node)
            # then we can implement the first condition
            left_of.set_right(middle)
            # we're not done yet
            # we get the row index of any node we used, since we are at the
            # same row
            at_row = base_node.get_row_index()
            # we set the row index for the middle node
            middle.set_row_index(at_row)
            # and we can just set the column index as the horizontal index
            # given
            middle.set_col_index(horiz_index)
            # and we just create a returning variable
            ret = middle

        return ret

    def _add_vertical_node(self, verti_index, content, base_node):
        '''
        (Matrix, int, float, MatrixNode) -> MatrixNode

        Adds and returns a MatrixNode, containing the content,
        vertically to the matrix in the same column as the base node;
        placed in the vertical index allocation.

        If there exists a node at the specified index of the column,
        this method will just replace its contents with the content given
        '''
        # We want the node above the given vertical index
        above = self._get_node_in_col_before_index(verti_index, base_node)
        # and we want the object below it
        just_below = above.get_down()
        # we check if the 'above' node is actually at the end
        # the below line is written because it couldn't fit... PEP8
        below_exists = just_below is not None

        # if there is a node below 'above' that has the same column index
        # as the vertical index given, we just change the contents
        # if not below_exists and above.get_row_index() == verti_index:
        #     above.set_contents(content)
        #     ret = above

        if below_exists and (just_below.get_row_index() == verti_index):
            just_below.set_contents(content)
            # create a returning node
            ret = just_below

        else:
            # we initialize a new node that is to be in the middle of
            # 'above' and 'just below'
            middle = MatrixNode(content, None, just_below)
            above.set_down(middle)
            # get the column index of any node here, since we are at the same
            # column
            at_col = base_node.get_col_index()
            # set the column and row indexes
            middle.set_col_index(at_col)
            middle.set_row_index(verti_index)
            ret = middle
        # this method is less commented than _add_horizontal_node() because
        # it is just a mirror of the two, but in a column context

        # return the node
        return ret

    def are_indexes_valid(self, row_index, col_index):
        '''
        (Matrix, int, int) -> None

        Determines whether or not the given row index or column index is
        invalid

        RAISES MatrixIndexError if the given row index is not in the range
        of the amount of rows there are in the matrix
        RAISES MatrixIndexError if the given column index is not in the range
        of the amount of columns there are in the matrix
        '''
        num_rows, num_cols = self.get_size()
        # The primary reason this method exists is because this code is ran
        # more often than it should within methods
        if not (row_index in range(num_rows)):
            raise MatrixIndexError('\nPlease insert a row index in the range\
 of 0 <= row index < ' + str(num_rows) + '\nYour row index input is: ' +
                                    str(row_index))
        if not (col_index in range(num_cols)):
            raise MatrixIndexError('\nPlease insert a column index in the\
 range of 0 <= column_index < ' + str(num_cols) + '\nYour column index\
 input is: ' + str(col_index))
        return None

    def _remove_element(self, row_index, col_index):
        '''
        (Matrix, int, int) -> None

        Removes a fixed, non-default element from the matrix. Does nothing
        if the element doesn't exist.
        '''
        # we have to start traversing from the head
        # we also have to remember that our helper methods
        # '_get_node_before_index' returns the node before our wanted
        # index from the head; we want the node directly at the index

        # so we use 2 variables, direct row/column locations
        direct_row = self._get_node_in_col_before_index(row_index + 1,
                                                        self._head)
        direct_col = self._get_node_in_row_before_index(col_index + 1,
                                                        self._head)
        # so, we're at a row, so we check if direct_row's index is the
        # passed row index, otherwise the element doesn't exist
        if (direct_row.get_row_index() == row_index):
            # we set up two nodes, one is on the left of the node we want to
            # delete
            on_left = self._get_node_in_row_before_index(col_index, direct_row)
            # the other is on the right
            on_right = on_left.get_right()
            # if on_right is None:
            #    on_left is at the end of the row; the node we want to delete
            #    doesn't exist
            # OR (implying that on_right is a node)
            # if on_right's column index isn't the same as the
            # column index passed:
            #    this isn't the node we want to delete
            if on_right is not None and on_right.get_col_index() == col_index:
                # currently, on_right is the node we want to delete
                # we just get the node after the node we want to delete
                on_right = on_right.get_right()
                # then we just unlink it horizontally to delete it
                on_left.set_right(on_right)
                # we don't care if on_right is None

            # because we have (potentially) deleted a node, the row index node
            # of the matrix might be a stray node; there is nothing to the
            # right of it
            if direct_row.get_right() is None:
                # we would have to delete the stray indexing node too
                # we get the node on top of the stray node
                top = self._get_node_in_col_before_index(row_index,
                                                         self._head)
                # we get the node below the stray node
                below = direct_row.get_down()
                # then we just delete the stray node by rerouting top's down
                # to below
                top.set_down(below)

        # we do the same thing, vertically
        if (direct_col.get_col_index() == col_index):
            # we set up two nodes, one is on top of the node we want to delete
            on_top = self._get_node_in_col_before_index(row_index,
                                                        direct_col)
            # and the other is on bottom
            on_bottom = on_top.get_down()
            # if on_bottom is None:
            #     on_top is at the end of the column; the node we want to
            #     delete doesn't exist
            exists = on_bottom is not None
            # OR
            # if on_bottom's row index isn't the same as the row index
            # passed:
            #     this isn't then node we want to delete either
            if exists and (on_bottom.get_row_index() == row_index):
                # currently, on_bottom is the node we want to delete
                # we just get the node below on bottom
                on_bottom = on_bottom.get_down()
                # then we just delete the node by unlinking it vertically
                on_top.set_down(on_bottom)

            # because we have (potentially) deleted a node, the column index
            # node within the matrix may be a stray node; there is nothing
            # below it
            if direct_col.get_down() is None:
                # we would have to delete the stray node too
                # we get the node just left of the stray node
                left = self._get_node_in_row_before_index(col_index,
                                                          self._head)
                # we get the node to the right of the stray node
                right = direct_col.get_right()
                # then we just set the left's right pointer to the right
                # to delete the stray node
                left.set_right(right)


class OneDimensionalMatrix(Matrix):
    '''A 1xn or nx1 matrix.
    (For the purposes of multiplication, we assume it's 1xn)'''

    def __init__(self, n, default=0, is_horizontal=True):
        '''(OneDimensionalMatrix, int, int, float) -> NoneType

        Creates a OneDimensionalMatrix.

        By default, the OneDimensionalMatrix is horizontal unless
        stated otherwise.
        By default, the OneDimensionalMatrix is populated by 0s unless stated
        otherwise

        RAISES MatrixDimensionError if n is not a positive integer
        '''
        # REPRESENTATION INVARIANT
        # OneDimensionalMatrix is a child of Matrix, and does what
        # a Matrix does
        # _num_elements is a positive integer that represents the number
        # of elements within the OneDimensionalMatrix
        # _is_horizontal is a boolean that indicates if this is a
        # horizontal matrix or not
        # if _is_horizontal is True:
        #     The size of the matrix is 1 by n
        # otherwise:
        #     the size of the matrix is n by 1

        if (n <= 0):
            raise MatrixDimensionError('n must be a positive integer')

        # if the user wants a horizontal matrix, we will just set m to 1
        if is_horizontal:
            # and we make it a 1 by n matrix
            Matrix.__init__(self, 1, n, default)
        else:
            # otherwise we make it a n by 1 matrix
            Matrix.__init__(self, n, 1, default)
        # added attributes
        self._is_horizontal = is_horizontal
        self._num_elements = n

    def get_item(self, i):
        '''(OneDimensionalMatrix, int) -> float
        Return the i'th item in this matrix

        RAISES MatrixDimensionError if i >= the number of elements in the
        OneDimensionalMatrix or if i < 0
        '''
        if (i not in range(self._num_elements)):
            raise MatrixDimensionError('Please input the index, i, in the\
 range of: \n0 <= i < ' + str(self._num_elements))
        # if the OneDimensionalMatrix is horizontal
        if self._is_horizontal:
            # we just get the return value of the 0th row and the
            # ith column; this doesn't break abstraction given the details
            # of the get_val docstring
            ret = self.get_val(0, i)
        else:
            # we just get the return value of the ith row and the 0th
            # column; this also doesn't break abstraction
            ret = self.get_val(i, 0)
        return ret

    def set_item(self, i, new_val):
        '''(OneDimensionalMatrix, int, float) -> NoneType
        Set the i'th item in this matrix to new_val
        RAISES MatrixDimensionError if i >= the number of elements in the
        OneDimensionalMatrix or if i < 0
        '''
        if (i not in range(self._num_elements)):
            raise MatrixDimensionError('Please input the index, i, in the\
 range of:\n 0 <= i < ' + str(self._num_elements))
        # if the OneDimensionalMatrix is horizontal
        if self._is_horizontal:
            # we just set the value at the 0th row and the ith column to not
            # break abstraction
            self.set_val(0, i, new_val)
        else:
            # we just set the value at the ith row and the 0th column to not
            # break abstraction
            self.set_val(i, 0, new_val)

    def multiply_matrix(self, mult_matrix):
        '''(OneDimensionalMatrix, Matrix) -> OneDimensionalMatrix

        Returns a 1 by m matrix that is a product of mult_matrix and this
        1 by n OneDimensionalMatrix

        The dimensions for this matrix must be 1 by n
        The dimensions for mult_matrix must be n by m

        RAISES MatrixDimensionError if the number of columns of this matrix
        is not equal to the number of rows of this matrix
        '''
        # first check if the number of elements is equal to the number
        # of rows there are in the passed matrix
        mult_num_rows, mult_num_cols = mult_matrix.get_size()
        if (self._num_elements != mult_num_rows):
            raise MatrixDimensionError('The number of columns of this matrix\
 is not equivalent to the number of rows of the passed matrix')
        # we somewhat reuse the multiplied matrix code
        ret_mat = OneDimensionalMatrix(mult_num_cols)
        # REMEMBER
        # C(i, j) = A(i, k) * B(k, j)
        # two loops; there is only 1 row
        for j in range(mult_num_cols):
            summer = 0
            for k in range(mult_num_rows):
                summer += self.get_item(k) * mult_matrix.get_val(k, j)
            ret_mat.set_item(j, summer)

        return ret_mat

    def get_num_elements(self):
        '''
        (OneDimensionalMatrix) -> int

        Returns the number of elements there are in the OneDimensionalMatrix
        '''
        # this is for the purpose of not breaking abstraction for the Matrix
        # class
        return self._num_elements


class SquareMatrix(Matrix):
    '''A matrix where the number of rows and columns are equal'''

    def __init__(self, n, default=0):
        '''
        (SquareMatrix, int, int, float) -> None

        Initializes a square n by n matrix.

        By default, this matrix will be populated with 0s.

        RAISES MatrixDimensionError if n is not a positive integer
        '''
        # REPRESENTATION INVARIANT
        # SquareMatrix is a child of Matrix, and does what a Matrix can
        # _sq_size is a positive integer that represents the number of rows
        # and the number of columns there are within the SquareMatrix
        # _sq_size by _sq_size are the dimensions of the SquareMatrix

        # check if m and n are the same integers
        if (n <= 0):
            raise MatrixDimensionError('n must be a positive integer!')
        # initialize the matrix
        Matrix.__init__(self, n, n, default)
        # we just get the square size of the matrix
        self._sq_size = n

    def transpose(self):
        '''(SquareMatrix) -> NoneType
        Transpose this matrix
        '''
        # create two loops using the Matrix's square size
        # one for the rows
        for i in range(self._sq_size):
            # the other for the columns, but we start at i
            for j in range(i, self._sq_size):
                # we get one value at (i, j)
                primary = self.get_val(i, j)
                # we get another value at (j, i)
                secondary = self.get_val(j, i)
                # and then the values are swapped
                self.set_val(i, j, secondary)
                self.set_val(j, i, primary)
                # thus, we have ended the transpose

    def get_diagonal(self):
        '''(Squarematrix) -> OneDimensionalMatrix

        Return a one dimensional matrix with the values of the diagonal
        of this matrix.

        The OneDimensionalMatrix returned is a 1 by n matrix
        '''
        # first, we initialize a one dimensional matrix
        ret_mat = OneDimensionalMatrix(self._sq_size)
        # then, we can go through only one loop, which is the Matrix's sq size
        for i in range(self._sq_size):
            # we get the contents
            cont = self.get_val(i, i)
            # then we set the content to the item at i
            ret_mat.set_item(i, cont)
        # then we return the one dimensional matrix
        return ret_mat

    def set_diagonal(self, new_diagonal):
        '''(SquareMatrix, OneDimensionalMatrix) -> NoneType
        Set the values of the diagonal of this matrix to those of new_diagonal

        RAISES MatrixDimensionError if the OneDimensionalMatrix's number of
        elements is not equal to the number of rows/cols there are in the
        SquareMatrix
        '''
        # we check if the number of elements is equal to our square size
        if (new_diagonal.get_num_elements() != self._sq_size):
            # raise an error if it isn't equal
            raise MatrixDimensionError('Please insert a OneDimensionalMatrix\
 with ' + str(self._sq_size) + ' elements')
        # we go into a for loop, in the range of the square size
        for i in range(self._sq_size):
            # we get the contents from the OneDimensionalMatrix
            cont = new_diagonal.get_item(i)
            # then we set the contents to the (i,i)th element
            self.set_val(i, i, cont)

    def get_square_size(self):
        '''
        (SquareMatrix) -> int

        Returns the number of rows/cols there are in the SquareMatrix
        '''
        return self._sq_size


class SymmetricMatrix(SquareMatrix):
    '''A Symmetric Matrix, where m[i, j] = m[j, i] for all i and j
    '''

    def set_val(self, i, j, new_val):
        '''(SymmetricMatrix, int, int, float) -> NoneType

        Sets the value of sym[i,j] and sym[j, i] to new_val for this matrix
        sym

        RAISES MatrixIndexError if (i >= number of rows in SymmetricMatrix) OR
        (i < 0)
        RAISES MatrixIndexError if (j >= number of cols in SymmetricMatrix) OR
        (j < 0)
        '''
        # check if the indexes are valid
        self.are_indexes_valid(i, j)
        # set the value accordingly to the (i,j)th element
        Matrix.set_val(self, i, j, new_val)
        # as well as the (j, i)th element to maintain symmetry
        Matrix.set_val(self, j, i, new_val)

    def set_row(self, row_num, new_row):
        '''
        (SymmetricMatrix, int, OneDimensionalMatrix) -> NoneType

        Sets the value(s) of the row_num'th row/col of this matrix to those
        of new_row

        RAISES MatrixIndexError if (row_num >= number of rows in
        SymmetricMatrix) OR (row_num < 0)

        RAISES MatrixDimensionError if the given OneDimensionalMatrix does
        not have the same amount of elements as there are rows in this matrix
        '''
        # check if the row_num is valid
        self.are_indexes_valid(row_num, 0)
        # then just set the row
        Matrix.set_row(self, row_num, new_row)
        # as well as the column to maintain symmetry
        Matrix.set_col(self, row_num, new_row)

    def set_col(self, col_num, new_col):
        '''
        (SymmetricMatrix, int, OneDimensionalMatrix) -> NoneType

        Set the value(s) of the row_num'th row/col of this matrix to those
        of new_row

        RAISES MatrixIndexError if (col_num >= number of columns in
        SymmetricMatrix) OR (col_num < 0)

        RAISES MatrixDimensionError if the given OneDimensionalMatrix does
        not have the same amount of elements as there are columns in this
        matrix
        '''
        # check if col_num is valid
        self.are_indexes_valid(0, col_num)
        # since set row does the same function, we call set_row
        self.set_row(col_num, new_col)

    def swap_rows(self, i, j):
        '''
        (SymmetricMatrix, int, int) -> NoneType
        Swap the values of rows i and j in this matrix and swap the columns
        i and j in the matrix to maintain symmetry

        RAISES MatrixIndexError if i or j is not in the range of the amount of
        rows there are in the matrix
        '''
        # check if i or j are invalid
        self.are_indexes_valid(i, 0)
        self.are_indexes_valid(j, 0)

        # get the row at i
        prim_row = self.get_row(i)
        # get the row at j
        seco_row = self.get_row(j)
        # then we swap the row and the column using Matrix's set
        Matrix.set_row(self, j, prim_row)
        Matrix.set_row(self, i, seco_row)
        # likewise we get the column at i and the column at j to maintain
        # symmetry
        prim_col = self.get_col(i)
        seco_col = self.get_col(j)
        # then we swap the row and column using the Matrix's set col
        Matrix.set_col(self, i, seco_col)
        Matrix.set_col(self, j, prim_col)

    def swap_cols(self, i, j):
        '''(SymmetricMatrix, int, int) -> NoneType
        Swap the values of columns i and j in this matrix and swap the rows
        i and j in the matrix to maintain symmetry

        RAISES MatrixIndexError if i or j is not in the range of the amount of
        columns there are in the matrix
        '''
        # check if i or j are invalid
        self.are_indexes_valid(0, i)
        self.are_indexes_valid(0, j)
        # we can just call swap_rows since it does the same thing
        self.swap_rows(i, j)


class DiagonalMatrix(SquareMatrix, OneDimensionalMatrix):
    '''A square matrix with 0 values everywhere but the diagonal'''

    def __init__(self, n, diagonal_val=0):
        '''
        (DiagonalMatrix, int, float) -> None

        Creates a diagonal n by n matrix. By default, the diagonal is
        set as all 0s unless stated otherwise.

        RAISES MatrixDimensionError if n is not a positive integer in order
        for it to be a Matrix
        '''
        # REPRESENTATION INVARIANT
        # DiagonalMatrix is a child of SquareMatrix and OneDimensionalMatrix
        # There are no added attributes, however;
        #
        # The size of the DiagonalMatrix is a SquareMatrix, n by n
        # if i != j:
        #     the value at (i, j) is 0, assuming both i and j are in range of n

        # check if n is a positive integer
        if (n <= 0):
            raise MatrixDimensionError('n must be a positive integer in order\
 to initialize the Diagonal Matrix')
        # we just initialize it like a SquareMatrix
        SquareMatrix.__init__(self, n)
        # then we create a OneDimensionalMatrix
        diagonal = OneDimensionalMatrix(n, diagonal_val)
        # then set the diagonal
        SquareMatrix.set_diagonal(self, diagonal)

    def set_val(self, i, j, new_val):
        '''(DiagonalMatrix, int, int, float) -> NoneType

        Sets the value of m[i,j] to new_val for this matrix, m

        RAISES MatrixInvalidOperationError if i is not equal to j; you can
        only change the values of the diagonal

        RAISES MatrixInvalidOperationError if i != j and new_val is not 0
        RAISES MatrixIndexError if (i >= number of rows in DiagonalMatrix) OR
        (i < 0)
        RAISES MatrixIndexError if (j >= number of cols in DiagonalMatrix) OR
        (j < 0)
        '''
        # check if the user is trying to set something outside the diagonal
        # that isnt 0
        self._val_checker(i, j, new_val)
        # check if the indexes are valid
        self.are_indexes_valid(i, j)
        # then we can just set the value
        SquareMatrix.set_val(self, i, j, new_val)

    def set_row(self, row_num, new_row):
        '''(DiagonalMatrix, int, OneDimensionalMatrix) -> NoneType

        Set the value(s) of the row_num'th row of this matrix to those
        of new_row

        RAISES MatrixIndexError if (row_num >= number of rows in
        DiagonalMatrix) OR (row_num < 0)

        RAISES MatrixDimensionError if the given OneDimensionalMatrix does
        not have the same amount of elements as there are rows in this matrix

        RAISES MatrixInvalidOperationError if new_row[not num_row] is not 0
        since this matrix is a diagonal matrix
        '''
        # check if the row_num is valid within the matrix
        self.are_indexes_valid(row_num, 0)
        # check if the new row has the same amount of elements as there are
        # columns in this square matrix
        sq_size = self.get_square_size()
        if (new_row.get_num_elements() != sq_size):
            raise MatrixDimensionError('Please insert a OneDimensional matrix\
 with ' + str(sq_size) + ' number of elements')
        # check if new_row[not row_num] is 0 for all elements in the
        # OneDimensionalMatrix
        for j in range(sq_size):
            # get the content
            cont = new_row.get_item(j)
            # then check if the value outside of the diagonal is 0
            self._val_checker(row_num, j, cont)
        # if all test passes, we can just set the row
        SquareMatrix.set_row(self, row_num, new_row)

    def set_col(self, col_num, new_col):
        '''(DiagonalMatrix, int, OneDimensionalMatrix) -> NoneType

        Set the value(s) of the col_num'th row of this matrix to those
        of new_col

        RAISES MatrixIndexError if (col_num >= number of columns in
        DiagonalMatrix) OR (col_num < 0)

        RAISES MatrixDimensionError if the given OneDimensionalMatrix does
        not have the same amount of elements as there are columns in this
        matrix

        RAISES MatrixInvalidOperationError if new_col[not num_col] is not 0
        since this matrix is a diagonal matrix
        '''
        # check if the col num is valid within the matrix
        self.are_indexes_valid(0, col_num)
        # check if the new column has the same amount of elements as there are
        # rows in this square matrix
        sq_size = self.get_square_size()
        if (new_col.get_num_elements() != self.get_square_size()):
            raise MatrixDimensionError('Please insert a OneDimensional matrix\
 with ' + str(sq_size) + ' number of elements')
        # check if new_col[not col_num] isn't at 0
        for i in range(sq_size):
            # get the content of the OneDimensionalMatrix
            cont = new_col.get_item(i)
            # then check if the value outside of the diagonal is 0
            self._val_checker(i, col_num, cont)
        # if all test pass, we can just set the column
        SquareMatrix.set_col(self, col_num, new_col)

    def swap_rows(self, i, j):
        '''(DiagonalMatrix, int, int) -> NoneType
        Swap the values of rows i and j in this matrix

        RAISES MatrixInvalidOperationError if i is not equal to j and the rows
        at i and j are not zero rows

        RAISES MatrixIndexError if i or j is not in the range of the amount of
        rows there are in the matrix
        '''
        # check if the indexes are valid
        self.are_indexes_valid(i, 0)
        self.are_indexes_valid(j, 0)
        # check if rows i and j are 0 rows
        prim_row = self.get_row(i)
        seco_row = self.get_row(j)
        # go in the range of the number of rows/cols
        # for now, we assume that both of them are
        prim_is_zero = True
        seco_is_zero = True
        for element in range(self.get_square_size()):
            # go through the row
            prim_cont = prim_row.get_item(element)
            seco_cont = seco_row.get_item(element)
            # and raise flags if either of them are non zero
            if prim_is_zero:
                prim_is_zero = prim_cont == 0
            if seco_is_zero:
                seco_is_zero = seco_cont == 0

        # if both either of them are not zero vectors, but not both we
        # raise an error
        seco_isnt = prim_is_zero and not seco_is_zero
        prim_isnt = not prim_is_zero and seco_is_zero
        if prim_isnt or seco_isnt:
            raise MatrixInvalidOperationError('You cannot swap these two rows')

        # otherwise we can assume that they are either both zero rows, or
        # they are both non zero
        # in this case if they are both nonzero and i != j then we should
        # raise an error
        if (i != j) and not (prim_isnt and seco_isnt):
            raise MatrixInvalidOperationError('You cannot swap these two rows')

    def swap_cols(self, i, j):
        '''(DiagonalMatrix, int, int) -> NoneType
        Swap the values of columns i and j in this matrix


        RAISES MatrixInvalidOperationError if i is not equal to j and the
        columns at i and j are not zero rows

        RAISES MatrixIndexError if i or j is not in the range of the amount of
        columns there are in the matrix
        '''
        # check if the indexes are valid
        self.are_indexes_valid(0, i)
        self.are_indexes_valid(0, j)
        # then get the columns
        prim_col = self.get_col(i)
        seco_col = self.get_col(j)

        # we create two booleans and assum that the columns are zero
        prim_is_zero = True
        seco_is_zero = True
        # we go through a loop in the range of the square size
        for element in range(self.get_square_size()):
            # go through the columns
            prim_element = prim_col.get_item(element)
            seco_element = seco_col.get_item(element)
            # and raise flags if either of them are non zero
            if prim_is_zero:
                prim_is_zero = prim_element == 0
            if seco_is_zero:
                seco_is_zero = seco_element == 0

        # if both either of them are not zero vectors, but not both, we
        # raise an error
        seco_isnt = prim_is_zero and not seco_is_zero
        prim_isnt = not prim_is_zero and seco_is_zero
        if prim_isnt or seco_isnt:
            raise MatrixInvalidOperationError('You cannot swap these two\
 columns')

        # otherwise we can assume that they are either both zero columns, or
        # they are both non zero
        # in this case if they are both nonzero and i != j then we should
        # raise an error
        if (i != j) and not (prim_isnt and seco_isnt):
            raise MatrixInvalidOperationError('You cannot swap these two\
 columns')

    def subtract_scalar(self, sub_val):
        '''
        (DiagonalMatrix, float) -> NoneType
        Subtracts all values within the diagonal of this matrix with sub_val
        '''
        # we go through a for loop, in the size of this matrix
        sq_size = self.get_square_size()

        for i in range(sq_size):
            # we get the value at (i, i); in the main diagonal
            value = self.get_val(i, i)
            # we subtract the value by the scalar
            value -= sub_val
            # then we re-set the value after it is subtracted
            self.set_val(i, i, value)

    def add_scalar(self, add_val):
        '''
        (DiagonalMatrix, float) -> NoneType
        Adds all values within the diagonal of this matrix with sub_val
        '''
        # we can literally call subtract but with add_val multiplied by
        # negative 1 since
        # a + b = a - (-b)
        add_val *= -1
        self.subtract_scalar(add_val)

    def get_item(self, index):
        '''
        (DiagonalMatrix, int) -> None

        Returns the value of the diagonal at the position indicated by the
        index

        RAISES MatrixIndexError if (index >= number of rows/cols in
        DiagonalMatrix) OR (index < 0)
        '''
        # check if the index is valid
        sq_size = self.get_square_size()
        # so check if it is in range
        if not (index in range(sq_size)):
            # if it isnt then we raise the rror
            raise MatrixIndexError('Please insert an index in the range of\
 0 <= index < ' + sq_size)
        # if we pass, we just return the value at (i, i)
        return SquareMatrix.get_val(self, index, index)

    def set_item(self, index, new_val):
        '''
        (DiagonalMatrix, int) -> None

        Sets the value of the diagonal at the position indicated by the index.

        RAISES MatrixIndexError if (index >= number of rows/cols in
        DiagonalMatrix) OR (index < 0)
        '''
        # check if the index is within range
        sq_size = self.get_square_size()
        # so check if it is in range
        if not (index in range(sq_size)):
            # if it isnt we raise the error
            raise MatrixIndexError('Please insert an index in the range of\
0 <= index < ' + sq_size)
        # if the user passes the condition, then we just set the value
        # at (i, i)
        SquareMatrix.set_val(self, index, index, new_val)

    def _val_checker(self, i, j, value):
        '''
        (DiagonalMatrix, int, int, float) -> None

        Determines, in the DiagonalMatrix, if the value at (i,j) is valid for
        the diagonal matrix

        RAISES MatrixInvalidOperationError if i != j and value is nonzero
        RAISES MatrixInvalidOperationError if i == j and value is 0
        '''
        outside = (i != j) and (value != 0)
        if outside:
            raise MatrixInvalidOperationError('You can only put 0s outside\
 the main diagonal')


class IdentityMatrix(DiagonalMatrix):
    '''A matrix with 1s on the diagonal and 0s everywhere else'''

    def __init__(self, n):
        '''
        (IdentityMatrix, int) -> None

        Initialize the identity matrix with n rows and n columns
        '''
        # REPRESENTATION INVARIANT
        # IdentityMatrix is a child of DiagonalMatrix and does what
        # a DiagonalMatrix can do
        # _size is a positive integer that represents the number of rows
        # or the number of columns within the matrix
        self._size = n

    def __str__(self):
        '''
        (IdentityMatrix) -> str

        Returns a string representation of the Matrix
        '''
        # create an output
        output = ''
        # We go through two loops
        # one for the rows
        for i in range(self._size):
            # one for the columns
            output += '|'
            for j in range(self._size):
                # 1s appear where i==j
                if (i == j):
                    output += '1'
                else:
                    output += '0'
                output += ' '
            output = output[:-1] + '|\n'
        output = output[:-1]
        return output

    def transpose(self):
        '''
        (IdentityMatrix) -> None

        Transposes the IdentityMatrix
        '''
        # the transpose of the identity matrix is itself so we do nothing
        return None

    def get_size(self):
        '''
        (IdentityMatrix) -> int, int

        Respectively returns the number of rows and number of columns there
        are in the matrix
        '''
        # return the size twice
        return self._size, self._size

    def get_square_size(self):
        '''
        (IdentityMatrix) -> int

        Returns the number of rows/columns there are in the matrix
        '''
        # return the size once
        return self._size

    def get_val(self, i, j):
        '''
        (IdentityMatrix, int, int) -> float

        Returns a value allocated in the (i,j)th position of the matrix
        '''
        # check if the index given is in the main diagonal
        if (i == j):
            # if it is then return a 1
            ret = 1
        else:
            # otherwise return nothing
            ret = 0
        return ret

    def set_val(self, i, j, new_val):
        '''
        (IdentityMatrix, int, int, float) -> None

        Sets new_val to the IdentityMatrix's (i,j)th position

        RAISES MatrixInvalidOperationError if i is not equal to j and new_val
        is not 0
        RAISES MatrixInvalidOperationError if i is equal to j and new_val
        is not 1
        RAISES MatrixIndexError if (i >= number of rows in IdentityMatrix) OR
        (i < 0)
        RAISES MatrixIndexError if (j >= number of cols in IdentityMatrix) OR
        (j < 0)
        '''
        # first check if the indexes are valid
        self.are_indexes_valid(i, j)
        # then we need to see if i == j and new_val != 1 as it breaks the
        # idea of the identity matrix
        # if it isn't then we cannot set the value
        if (i == j) and (new_val != 1):
            raise MatrixInvalidOperationError('At the given indexes, the value\
 must be 1 in order for it to be an identity matrix')
        # and check if i != j and new_val != 0 as it would break the idea of
        # an identity matrix
        if (i != j) and (new_val != 0):
            raise MatrixInvalidOperationError('At the given indexes, the value\
 must be 0 in order for it to be an identity matrix')
        # no need to do anything else here

    def get_row(self, n):
        '''
        (IdentityMatrix, int) -> OneDimensionalMatrix

        Returns the nth row of the IdentityMatrix
        '''
        # first check if the row index is valid
        self.are_indexes_valid(n, 0)
        # then we create a OneDimensionalMatrix
        ret_mat = OneDimensionalMatrix(self._size)
        # we just set the item at n as 1
        ret_mat.set_item(n, 1)
        # then we can just return the matrix
        return ret_mat

    def set_row(self, n, new_row):
        '''
        (IdentityMatrix, int, OneDimensionalMatrix) -> None

        Sets the nth row of the IdentityMatrix to the new_row

        RAISES MatrixInvalidOperationError if the new row isn't a
        OneDimensionalMatrix with 1 at the nth position and 0 elsewhere

        RAISES MatrixDimensionError if the given OneDimensionalMatrix does
        not have the same amount of elements as there are rows in this matrix

        RAISES MatrixIndexError if (n >= number of rows in IdentityMatrix) OR
        (n < 0)
        '''
        # check if the row index is valid
        self.are_indexes_valid(n, 0)
        # then check the size of the row
        new_row_size = new_row.get_num_elements()
        # check if the size of the row is equal to the size of the identity
        # matrix
        if (new_row_size != self._size):
            # if they aren't then we raise an error that they should insert
            # a row with the same size
            raise MatrixInvalidOperation('Please insert a OneDimensionalMatrix\
 with ' + str(self._size) + ' elements')
        # then we have to check if the row has 1 in the nth position and 0
        # everywhere else
        for i in range(new_row_size):
            # get the element at i
            cont = new_row.get_item(i)
            # check if it is 0 everywhere else but the content is 0
            if (i != n) and (cont != 0):
                raise MatrixInvalidOperationError('The identity matrix must\
 have 0s outside of the diagonal')
            # at the same time, we have to check that in the nth position,
            # the value is 1
            if (i == n) and (cont != 1):
                raise MatrixInvalidOperationError('The identity matrix must\
 have 1s within the main diagonal')
        # no need to do anything else

    def get_col(self, n):
        '''
        (IdentityMatrix, int) -> OneDimensionalMatrix

        Returns the nth column of the IdentityMatrix
        '''
        # check if n is a valid column index
        self.are_indexes_valid(0, n)
        # if it is, we just create a OneDimensionalMatrix
        ret_mat = OneDimensionalMatrix(self._size, 0, False)
        # set the item, 1, at the nth position
        ret_mat.set_item(n, 1)
        # then return the matrix
        return ret_mat

    def set_col(self, n, new_col):
        '''
        (IdentityMatrix, int, OneDimensionalMatrix) -> None

        Sets the nth column of the IdentityMatrix to the new_col

        RAISES MatrixInvalidOperationError if the new column isn't a
        OneDimensionalMatrix with 1 at the nth position and 0 elsewhere

        RAISES MatrixDimensionError if the given OneDimensionalMatrix does
        not have the same amount of elements as there are rows in this matrix

        RAISES MatrixIndexError if (n >= number of cols in IdentityMatrix) OR
        (n < 0)
        '''
        # check if the column index is valid
        self.are_indexes_valid(0, n)
        # get the size of the column matrix
        new_col_size = new_col.get_num_elements()
        # check if the new_col's number of elements are the same as this
        # matrix's size, otherwise it is invalid
        if (self._size != new_col_size):
            raise MatrixDimensionError('Please insert a OneDimensionalMatrix\
 that has ' + str(self._size) + ' number of elements')
        # then we have to check every item in the column
        for j in range(new_col_size):
            # we need to get the item
            cont = new_col.get_item(j)
            # check if the content is 0 and j is not equal to the passed index
            if (cont != 0) and (j != n):
                raise MatrixInvalidOperationError('The matrix must have 0s\
 outside of the main diagonal')
            # also, since it is an identity matrix check if the contents arent
            # 1 and it is lying on the main diagonal
            if (cont != 1) and (j == n):
                raise MatrixInvalidOperationError('The matrix must have 1s\
 along the main diagonal')
        # no need to do anything else

    def swap_rows(self, i, j):
        '''
        (IdentityMatrix, int, int) -> None

        Swaps the i and j rows of the matrix

        RAISES MatrixInvalidOperationError if i is not equal to j
        RAISES MatrixIndexError if (i >= number of rows in IdentityMatrix) OR
        (i < 0)
        RAISES MatrixIndexError if (j >= number of rows in IdentityMatrix) OR
        (j < 0)
        '''
        # first check if the indexes are valid
        self.are_indexes_valid(i, 0)
        self.are_indexes_valid(j, 0)
        # we cannot swap two different rows
        if (i != j):
            raise MatrixInvalidOperationError('The identity matrix cannot swap\
 two rows')
        # no need to do anything after

    def swap_cols(self, i, j):
        '''
        (IdentityMatrix, int, int) -> None

        Swaps the i and j rows of the matrix

        RAISES MatrixInvalidOperationError if i is not equal to j
        RAISES MatrixIndexError if (i >= number of cols in IdentityMatrix) OR
        (i < 0)
        RAISES MatrixIndexError if (j >= number of cols in IdentityMatrix) OR
        (j < 0)
        '''
        # check if the indexes are valid
        self.are_indexes_valid(0, i)
        self.are_indexes_valid(0, j)
        # check if they are not equal
        if (i != j):
            raise MatrixInvalidOperationError('You cannot swap two different\
 columns within an identity matrix')

    def add_scalar(self, add_value):
        '''
        (IdentityMatrix, float) -> None

        Adds the add_value to all values of the matrix

        RAISES MatrixInvalidOperationError if add_value is not 0
        '''
        # check if the value passed is non zero
        if (add_value != 0):
            # if it isn't then we aren't allowed to add values to the matrix
            raise MatrixInvalidOperationError('You cannot add values to the\
 identity matrix other than 0')
        # no need to do anything after

    def subtract_scalar(self, sub_value):
        '''
        (IdentityMatrix, float) -> None

        Subtracts the sub_value to all values of the matrix

        RAISES MatrixInvalidOperationError if sub_value is not 0
        '''
        # check if the value passed is non zero
        if (sub_value != 0):
            # if it isn't then we aren't allowed to subtract values to the
            # matrix
            raise MatrixInvalidOperationError('You cannot subtract values to\
 the identity matrix other than 0')

    def multiply_scalar(self, mult_value):
        '''
        (IdentityMatrix, float) -> None

        Multiplies the mult_value to all values of the matrix

        RAISES MatrixInvalidOperationError if mult_value is not 1
        '''
        # check if the value passed is not 1
        if (mult_value != 1):
            # if it isn't then we aren't allowed to multiply any values to the
            # matrix
            raise MatrixInvalidOperationError('You cannot multiply values\
 other than 1 to the identity matrix')

    def add_matrix(self, adder_matrix):
        '''
        (IdentityMatrix, Matrix) -> Matrix

        Returns adder_matrix, but with its values in its diagonal added by
        1

        RAISES MatrixDimensionError if both matrices don't have the same
        size
        '''
        # we check if the sizes are the same
        identity_size = self.get_size()
        adder_size = adder_matrix.get_size()
        if (identity_size != adder_size):
            raise MatrixDimensionError('Both Matrices dont have the same\
 size')
        # in this case, we make a new matrix, both with the row and column
        # of the same size
        ret_mat = Matrix(self._size, self._size)
        # then we just go through two loops
        for i in range(self._size):
            for j in range(self._size):
                # we get the contents of the passed matrix
                element = adder_matrix.get_val(i, j)
                # if i == j, the main diagonal, then we just add 1 to the
                # element
                if (i == j):
                    element += 1
                # then just set value of the returning matrix at (i, j) to
                # the contents
                ret_mat.set_val(i, j, element)
        # then return the matrix
        return ret_mat

    def multiply_matrix(self, mult_mat):
        '''
        (IdentityMatrix, Matrix) -> Matrix
        Returns a n by m matrix that is a product of this matrix and
        mult matrix

        The dimensions for this matrix must be n by r
        The dimensions for mult_matrix must be r by m

        RAISES MatrixDimensionError if the number of columns of this matrix
        is not equal to the number of rows of this matrix
        '''
        # first check if the sizes are okay
        # we just get the number of rows in the multiplication matrix
        num_rows, num_cols = mult_mat.get_size()
        # then check if the number of rows is equal to the number of columns
        # there are in this matrix;
        # remember identity matrix is n by n
        # the multiplying matrix is n by r
        if (num_rows != self._size):
            raise MatrixDimensionError('The given matrix\'s rows doesnt equal\
 the number of columns there are in this identity matrix')
        # we would literally return the same matrix since an identity matrix
        # multiplied by another matrix is the same matrix passed
        # but we're returning a new matrix
        ret_mat = Matrix(num_rows, num_cols)
        # we set up two loops, one for rows and one for columns
        for i in range(num_rows):
            for j in range(num_cols):
                # we get the element at the passed matrix's (i, j) value
                element = mult_mat.get_val(i, j)
                # then we set the value to the returning matrix
                ret_mat.set_val(i, j, element)
        # then we return the returning matrix
        return ret_mat

    def get_item(self, index):
        '''
        (IdentityMatrix, int) -> float

        Returns the value of the diagonal of this matrix at the given index

        RAISES MatrixIndexError if the given index < 0 OR index >= number of
        rows/cols
        '''
        # we just check if the index is valid since we are getting an item
        # from the diagonal of the matrix
        if not (index in range(self._size)):
            # we raise an error
            raise MatrixIndexError('Please insert an index in the range of\
 0 <= index < ' + str(self._size))
        # then we just return 1 since all values must be 1
        return 1

    def set_item(self, index, new_val):
        '''
        (IdentityMatrix, int, float) -> None

        Sets new_val to the IdentityMatrix at the position of the diagonal,
        indicated by the given index

        RAISES MatrixOperationError if the new value is not 1
        RAISES MatrixIndexError if the given index < 0 OR index >= number of
        rows/cols
        '''
        # we just check if the index is valid since we are getting an item
        # from the diagonal of the matrix
        if not (index in range(self._size)):
            # we raise an error
            raise MatrixIndexError('Please insert an index in the range of\
 0 <= index < ' + str(self._size))
        # then just check if the value isn't 1
        if (new_val != 1):
            # if it isn't then we have to raise an error
            raise MatrixOperationError('The passed value must be 1 in the\
 identity matrix')

    def set_diagonal(self, new_dia):
        '''
        (IdentityMatrix, OneDimensionalMatrix)

        Replaces the main diagonal of the matrix with the given OneDimensional
        Matrix's values

        RAISES MatrixInvalidOperationError if all elements of the
        OneDimensionalMatrix is not 1
        RAISES MatrixDimensionError if the number of elements in new_dia is
        not equal to the number of rows/cols in this matrix
        '''
        # first, we have to check the length of the OneDimensionalMatrix
        new_dia_length = new_dia.get_num_elements()
        # then we just have to check if the length of the OneDimensionalMatrix
        # is equal to the size of the identity matrix
        if (new_dia_length != self._size):
            # if it isn't then we raise an error since we cannot fit/expand the
            # OneDimensionalMatrix to the identity matrix
            raise MatrixDimensionError('Please insert a OneDimensionalmatrix\
 with ' + str(self._size) + ' elements')
        # then we have to check if the new diagonal is all 1s
        # so we set up 1 loop
        for i in range(new_dia_length):
            # we get the element at i
            element = new_dia.get_item(i)
            # then we check if it isn't 1
            if (element != 1):
                # if it isn't then we have to raise an error
                raise MatrixInvalidOperationError('All values along the main\
 diagonal must be 1')
        # no need to do anything after

    def get_diagonal(self):
        '''
        (IdentityMatrix) -> OneDimensionalMatrix

        Returns the OneDimensionalMatrix representation of the main diagonal
        '''
        # just create a one dimensional matrix with all values at 1
        ret_mat = OneDimensionalMatrix(self._size, 1)
        # then just return it
        return ret_mat
