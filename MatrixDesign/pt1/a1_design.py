class UnequalInnerProductException(Exception):
    '''
    An exception to be raise when the primary's Matrix's number of columns is
    not equal to the secondary Matrix's number of rows for matrix
    multiplication
    '''


class InvalidSizeException(Exception):
    '''
    An exception to be raised when the Matrix's size is not valid to initialize
    a matrix or to do a mathetmatical operation on a matrix
    '''


class InsufficientElementsException(Exception):
    '''
    An exception to be raised when there aren't enough elements in a list to
    populate the row/column/diagonal of a matrix
    '''


class ExcessElementsException(Exception):
    '''
    An exception to be raised when there are too many elements in a list to
    populate the row/column/diagonal of a matrix
    '''


class NotMathematicalException(Exception):
    '''
    An exception to be raised when a matrix method is required to be a
    mathematical matrix, but isn't a mathematical matrix
    '''


class InvalidRowIndexException(Exception):
    '''
    An exception to be raised when the row index given is not in range with
    the number of rows there are within the matrix
    '''


class InvalidColumnIndexException(Exception):
    '''
    An exception to be raised when the column index given is not in range with
    the number of columns there are within the matrix
    '''


class InvalidIndexException(Exception):
    '''
    An exception to be raised when the index given is not in range with
    the number of elements within a OneDimensionalMatrix
    '''


class Matrix():
    '''
    A generic matrix that has m rows and n columns that have elements
    easily located within them. This class needs the number of rows and
    the number of columns in order for it to be initialized.
    '''

    def __init__(self, num_rows, num_cols):
        '''
        (Matrix, int, int) -> None

        Given the number of rows and the number of columns, initialize the
        matrix. By default, all elements are populated by the integer 0.

        If the number of rows is the same as the number of columns, the
        Matrix will turn into a SquareMatrix, but still has properties of a
        generic Matrix.

        If the number of rows or the numbers of columns is set at 1, the
        Matrix will turn into a OneDimensionalMatrix, but still has properties
        of a generic Matrix.

        If the number of rows and the number of columns is set at 1, the
        Matrix will turn into a OneElementMatrix, but still has properties of a
        generic Matrix.

        RAISES InvalidSizeException if the number of rows or the number of
        columns is <= 0
        '''

    def __add__(self, secondary_matrix):
        '''
        (Matrix, Matrix) -> Matrix

        Given two mathematical matrices of the same size (m rows by n columns),
        matrix A and matrix B, this method will return a new matrix by getting
        matrix A's (i,j) element added by matrix B's (i,j) element for the
        new matrix's (i,j) element. The matrix returned will be the same size
        of matrix A and matrix B.

        i represents a row for all m rows of both matrices, and
        j represents a column for all n columns of both matrices.

        If either matrix is not a mathematical matrix, then matrix A will
        concatenate matrix B's elements respectively

        RAISES InvalidSizeException if the matrices are not the same size
        '''

    def __sub__(self, secondary_matrix):
        '''
        (Matrix, Matrix) -> Matrix

        Given two mathematical matrices of the same size (m rows by n columns),
        matrix A and matrix B, this method will return a new matrix by
        subtracting matrix A's (i,j) element from matrix B's (i,j)
        element for the new matrix's (i,j) element. The matrix returned will be
        the same size of matrix A and matrix B.

        i represents a row for all m rows of both matrices, and
        j represents a column for all n columns of both matrices.

        RAISES InvalidSizeException if the matrices are not the same size
        RAISES NotMathematicalException if both matrices are not mathematical
        matrices
        '''

    def __mul__(self, secondary_matrix):
        '''
        (Matrix, Matrix) -> Matrix

        Given one mathematical Matrix with a size of m rows by n columns,
        Matrix A, and given another mathematical matrix with a size of n rows
        and r columns, Matrix B;

        This method will return a new matrix where the (i,j) element is defined
        by summing Matrix A's (i,k) element multiplied by Matrix B's (k,j)
        element within all elements of Matrix A's kth column and
        Matrix B's kth row.

        i represents a row for all m rows in Matrix A,
        j represents a column for all r columns in Matrix B
        k represents the column for all n columns in Matrix A and
        k represents the row for all n rows in Matrix B

        The new matrix's size will be m rows by r columns.

        RAISES UnequalInnerProductException if the first matrix's size of
        columns is not equal to the second matrix's size of rows.
        RAISES NotMathematicalException if both matrices are not mathematical
        matrices
        '''

    def __eq__(self, secondary_matrix):
        '''
        (Matrix, Matrix) -> Matrix

        Given two matrices, this method will determine if both matrices are
        equal; determined by its size and its respective elements.
        '''

    def __str__(self):
        '''
        (Matrix) -> str

        Returns a string representation of the matrix.
        '''

    def get_size(self):
        '''
        (Matrix) -> int, int

        Returns the size of the matrix; first component given is the number of
        rows and the second component given is the number of columns
        '''

    def get_col(self, column_index):
        '''
        (Matrix, int) -> list of Objects

        This method will return the matrix's column, indicated by the
        column_index, as a list of objects.
        RAISES InvalidColumnIndexException if the column_index is not
        within range of the number of columns of the Matrix
        '''

    def set_col(self, column_index, new_col):
        '''
        (Matrix, int, list of Objects) -> None

        Given a list replicated as a column, this method will set a matrix's
        column to the given list, indicated by the column_index

        RAISES InvalidColumnIndexException if the column_index is not
        within range of the number of columns of the Matrix
        RAISES ExcessElementsException if there are too many elements within
        the given list to fit into a singular column of the Matrix
        RAISES InsufficientElementsException if there are too little elements
        within the given list to fit into a singular column of the Matrix
        '''

    def get_row(self, row_index):
        '''
        (Matrix, int) -> list of Objects

        This method will return the matrix's row, indicated by the
        row_index, as a list of objects.

        RAISES InvalidRowIndexException if the row_index is not
        within range of the number of rows of the Matrix
        '''

    def set_row(self, row_index, new_row):
        '''
        (Matrix, int, list of Objects) -> None

        Given a list replicated as a row, this method will set a matrix's
        row to the given list, indicated by the row_index

        RAISES InvalidRowIndexException if the row_index is not
        within range of the number of rows of the Matrix
        RAISES ExcessElementsException if there are too many elements within
        the given list to fit into a singular row of the Matrix
        RAISES InsufficientElementsException if there are too little elements
        within the given list to fit into a singular row of the Matrix
        '''

    def get_value(self, row_index, column_index):
        '''
        (Matrix, int, int) -> Object

        Given a row_index and a column_index, this method returns an object
        within the matrix at its given respective indexes.

        RAISES InvalidRowIndexException if the row_index is not
        within range of the number of rows of the Matrix
        RAISES InvalidColumnIndexException if the column_index is not
        within range of the number of columns of the Matrix
        '''

    def set_value(self, row_index, column_index, new_value):
        '''
        (Matrix, int, int, Object) -> None

        Given a row_index, a column_index, and an object, this method will
        replace the value at the given indexes with the given object.

        RAISES InvalidRowIndexException if the row_index is not
        within range of the number of rows of the Matrix
        RAISES InvalidColumnIndexException if the column_index is not
        within range of the number of columns of the Matrix
        '''

    def swap_col(self, prime_column_index, secondary_column_index):
        '''
        (Matrix, int, int) -> None

        Given two indexes within a matrix, this method will swap two columns
        within the Matrix

        RAISES InvalidColumnIndexException if the either index is not
        within range of the number of columns of the Matrix
        '''

    def swap_row(self, prime_row_index, secondary_row_index):
        '''
        (Matrix, int, int) -> None

        Given two indexes within a matrix, this method will swap two rows
        within the Matrix

        RAISES InvalidRowIndexException if the either index is not
        within range of the number of columns of the Matrix
        '''

    def is_mathematical_matrix(self):
        '''
        (Matrix) -> bool

        Determines if the matrix has all of its elements has a numerical
        instance, labelling it as a mathematical matrix.
        '''

    def transpose(self):
        '''
        (Matrix) -> Matrix

        Returns a Matrix of itself, but with it's rows and columns
        interchanged.

        Consider matrix A, whose size is m rows by n columns. A's transpose
        will have a size of n columns and m rows; matrix A's ith row is
        matrix A^T's jth row, and matrix A's jth row is matrix A^T's ith row.

        ith means a row/column in all of m
        jth means a row/column in all of n
        '''

    def mul_scalar(self, scalar):
        '''
        (Matrix, float) -> Matrix

        Returns a Matrix of itself, but with all elements multiplied by the
        scalar. The matrix must be a mathematical matrix.

        RAISES NotMathematicalException if the matrix is not a mathematical
        matrix
        '''


class OneDimensionalMatrix(Matrix):
    '''
    A matrix that has either 1 row or 1 column. The Matrix must be given its
    length in order for it to become initialized. By default, the
    OneDimensionalMatrix is a horizontal matrix unless stated by an
    additional parameter of the boolean 'False'.
    '''

    def __init__(self, matrix_length, horizontal_matrix=True):
        '''
        (OneDimensionalMatrix, int, bool) -> None

        Given the length of the matrix, initialize the OneDimensionalMatrix.
        By default, the matrix is a horizontal matrix unless stated by the
        boolean as False.

        By default, all elements are populated by the integer 0.

        If the matrix length is set at 1, then it is set as a OneElementMatrix,
        but still has properties of a OneDimensionalMatrix.

        RAISES InvalidSizeException if the matrix_length <= 0.
        '''

    def get_ele(self):
        '''
        (OneDimensionalMatrix, int) -> Object

        Given an elemental index, this method returns an object from the
        matrix at the indicated index.

        RAISES InvalidIndexException if the index is not within the range
        of the number of elements there are in the matrix
        '''

    def set_ele(self, index, new_value):
        '''
        (OneDimensionalMatrix, int, Object) -> None

        Given an elemental index, and an object, this method will replace
        the object, as indicated by the elemental index, with the object
        given.

        RAISES InvalidIndexException if the index is not within the range
        of the number of elements there are in the matrix
        '''

    def set_all(self, set_vector):
        '''
        (OneDimensionalMatrix, list of Objects) -> None

        Given a list of objects that replicates the OneDimensionalMatrix,
        this method will replace all elements within the matrix with
        the given list's components respectively.

        RAISES ExcessElementsException if there are too many elements within
        the given list to fit into the OneDimensionalMatrix
        RAISES InsufficientElementsException if there are too little elements
        within the given list to fit into the OneDimensionalMatrix
        '''

    def get_all(self):
        '''
        (OneDimensionalMatrix) -> List of Objects

        Returns all elements as a list of objects
        '''

    def swap(self, primary_index, secondary_index):
        '''
        (OneDimensionalMatrix, int, int) -> None

        Interchanges two elements within the OneDimensionalMatrix, indicated
        with the given two indexes.

        RAISES InvalidIndexException if the indexes are not within the range
        of the number of elements there are in the matrix
        '''

    def is_vertical(self):
        '''
        (OneDimensionalMatrix) -> bool

        Determine if the OneDimensionalMatrix is a vertical matrix
        '''

    def is_horizontal(self):
        '''
        (OneDimensionalMatrix) -> bool

        Determine if the OneDimensionalMatrix is a horizontal matrix
        '''


class SquareMatrix(Matrix):
    '''
    A matrix whose sides are equal to each other. The Matrix must be given its
    length of one side in order for it to become initialized.
    '''

    def __init__(self, square_dimension, symmetric=False):
        '''
        (SquareMatrix, int, bool) -> None

        Given the size of one side of the square matrix, initialize a
        square matrix. By default, the SquareMatrix is not symmetric unless
        stated by the boolean set to True.

        By default, all elements are populated by the integer 0.

        If the given side is at size 1, then the matrix will become a
        OneElementMatrix, but still has properties of a SquareMatrix.

        RAISES InvalidSizeException if the square_dimension <= 0.
        '''

    def identity_me(self, diagonal=1):
        '''
        (SquareMatrix, Object) -> None

        Sets all elements in the main diagonal as the object given, otherwise
        the main diagonal will be populated with the integer 1 by default. All
        other elements outside of the main diagonal will be set at 0.
        '''

    def get_diagonal(self):
        '''
        (SquareMatrix) -> List of Objects

        Returns the main diagonal as a list.
        '''
    def set_diagonal(self, new_diagonal):
        '''
        (SquareMatrix, List of Objects) -> None

        Given a list of objects, this method sets the main diagonal given the
        list of objects.

        RAISES ExcessElementsException if there are too many elements within
        the given list to fit into the main diagonal of the SquareMatrix
        RAISES InsufficientElementsException if there are too little elements
        within the given list to fit into the main diagonal of the SquareMatrix
        '''

    def determinant(self):
        '''
        (SquareMatrix) -> Float

        If the square matrix has 2 rows and 2 columns and is a mathematical
        matrix, this method returns the determinant of the matrix.

        RAISES NotMathematicalException if the square matrix is not a
        mathematical matrix
        RAISES InvalidSizeException if the square matrix does not have 2 rows
        and 2 columns
        '''

    def set_value(self, row_index, column_index, new_value):
        '''
        (SquareMatrix, int, int, Object) -> None

        Given a row_index, a column_index, and an object, this method will
        replace the value at the given indexes with the given object.

        If the SquareMatrix has symmetric properties then it will also
        replace the value at the given indexes with the given object,
        but with the indexes interchanged to maintain the SquareMatrix's
        symmetry.

        RAISES InvalidRowIndexException if the row_index is not
        within range of the number of rows of the Matrix
        RAISES InvalidColumnIndexException if the column_index is not
        within range of the number of columns of the Matrix
        '''

    def set_row(self, row_index, new_row):
        '''
        (SquareMatrix, int, list of Objects) -> None

        Given list replicated as a column, this method will set a matrix's
        column to the given list, indicated by the row_index

        If the matrix has symmetric properties, the respective column
        indicated by the row_index, will update its elements to the
        new row to maintain symmetry.

        RAISES InvalidRowIndexException if the row_index is not
        within range of the number of rows of the Matrix
        RAISES ExcessElementsException if there are too many elements within
        the given list to fit into a singular row of the Matrix
        RAISES InsufficientElementsException if there are too little elements
        within the given list to fit into a singular row of the Matrix
        '''

    def set_col(self, column_index, new_column):
        '''
        (SquareMatrix, int, list of Objects) -> None

        Given list replicated as a column, this method will set a matrix's
        column to the given list, indicated by the column_index

        If the matrix has symmetric properties, the respective row
        indicated by the column_index will update its elements to the new
        column to maintain symmetry.

        RAISES InvalidColumnIndexException if the column_index is not
        within range of the number of columns of the Matrix

        RAISES ExcessElementsException if there are too many elements within
        the given list to fit into a singular column of the Matrix
        RAISES InsufficientElementsException if there are too little elements
        within the given list to fit into a singular column of the Matrix
        '''

    def swap_col(self, prime_column_index, secondary_column_index):
        '''
        (SquareMatrix, int, int) -> None

        Given two indexes within a matrix, this method will swap two columns
        within the SquareMatrix.

        If the SquareMatrix is symmetric, then this method will also swap
        the rows indicated by the two indexes given to keep the SquareMatrix's
        symmetry

        RAISES InvalidColumnIndexException if the either index is not
        within range of the number of columns of the Matrix
        '''

    def swap_row(self, prime_row_index, secondary_row_index):
        '''
        (SquareMatrix, int, int) -> None

        Given two indexes within a matrix, this method will swap two rows
        within the Matrix

        If the SquareMatrix is symmetric, then this method will also swap
        the columns indicated by the two indexes given to keep the
        SquareMatrix's symmetry

        RAISES InvalidRowIndexException if the either index is not
        within range of the number of columns of the Matrix
        '''

    def has_symmetric_properties(self):
        '''
        (SquareMatrix) -> bool

        Determines if the SquareMatrix has symmetric properties, as initially
        determined
        '''


class OneElementMatrix(SquareMatrix, OneDimensionalMatrix):
    '''
    A Matrix that only has 1 element in it, and is considered a
    OneDimensionalMatrix and a SquareMatrix. This special matrix is both
    a horizontal and a vertical matrix, and is considered symmetric.
    '''

    def __init__(self):
        '''
        (OneElementMatrix) -> None

        Initialize the OneElementMatrix. By default its one element is
        populated as the integer 0.
        '''

    def set_me(self, new_value):
        '''
        (OneElementMatrix, Object) -> None

        Given a new value, this method will set its one element to the given
        value
        '''

    def get_me(self):
        '''
        (OneElementMatrix) -> Object

        Returns the one element that is in the matrix
        '''
