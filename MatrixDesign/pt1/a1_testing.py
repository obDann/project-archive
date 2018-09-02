from a1_design import *
import unittest


class TestMatrixInitialization(unittest.TestCase):

    def setUp(self):
        self._basic_matrix = Matrix(3, 2)
        self._basic_row, self._basic_col = self._basic_matrix.get_size()

    def test_basic_row_size_matrix(self):
        self.assertEqual(self._basic_row, 3, 'The row size should be 3')

    def test_basic_row_size_matrix(self):
        self.assertEqual(self._basic_col, 2, 'The column size should be 2')

    def test_all_elements_zero_row(self):
        expected_row = [0, 0]
        for i in range(1, self._basic_row + 1):
            self.assertEqual(self._basic_matrix.get_row(i), expected_row)

    def test_auto_square_matrix(self):
        # set up a 2 by 2 matrix, making it a square matrix
        sq_matrix = Matrix(2, 2)
        # check if the instance is a matrix as it is a parent
        is_matrix = isinstance(sq_matrix, Matrix)
        is_sq = isinstance(sq_matrix, SquareMatrix)
        self.assertTrue(is_matrix, 'This matrix is not a matrix')
        self.assertTrue(is_sq, 'This matrix is not a square matrix')

    def test_auto_1_dim_matrix_horiz(self):
        # set up a 1 by x matrix, making it a one dimensional matrix
        one_dim_horiz = Matrix(1, 5)
        is_matrix = isinstance(one_dim_horiz, Matrix)
        is_1d = isinstance(one_dim_horiz, OneDimensionalMatrix)
        self.assertTrue(is_matrix, 'This matrix is not a matrix')
        self.assertTrue(is_1d, 'This matrix is not a OneDimensionalMatrix')

    def test_auto_1_dim_matrix_verti(self):
        # set up a x by 1 matrix, making it a one dimensional matrix
        one_dim_horiz = Matrix(20, 1)
        is_matrix = isinstance(one_dim_horiz, Matrix)
        is_1d = isinstance(one_dim_horiz, OneDimensionalMatrix)
        self.assertTrue(is_matrix, 'This matrix is not a matrix')
        self.assertTrue(is_1d, 'This matrix is not a OneDimensionalMatrix')

    def test_one_element_matrix(self):
        # set up a 1 by 1 matrix, making it a one element matrix and a child
        # of OneDimensionalMatrix and SquareMatrix
        one_element_matrix = Matrix(1, 1)
        is_matrix = isinstance(one_element_matrix, Matrix)
        is_sq = isinstance(one_element_matrix, SquareMatrix)
        is_1d = isinstance(one_element_matrix, OneDimensionalMatrix)
        is_1e = isinstance(one_element_matrix, OneElementMatrix)
        self.assertTrue(is_matrix, 'This matrix is not a matrix')
        self.assertTrue(is_sq, 'This matrix is not a SquareMatrix')
        self.assertTrue(is_1d, 'This matrix is not a OneDimensionalMatrix')
        self.assertTrue(is_1e, 'This matrix is not a OneElementMatrix')

    def test_no_columns_matrix(self):
        self.assertRaises(InvalidSizeException, Matrix, 20, 0)

    def test_no_rows_matrix(self):
        self.assertRaises(InvalidSizeException, Matrix, 0, 20)

    def test_neg_columns_matrix(self):
        self.assertRaises(InvalidSizeException, Matrix, 1, -20)

    def test_neg_rows_matrix(self):
        self.assertRaises(InvalidSizeException, Matrix, -10, 3)


class TestMatrixAddition(unittest.TestCase):

    def test_zero_matrix_addition(self):
        # create a 3 by 2, 0 populated matrix
        zero_matrix = Matrix(3, 2)
        # create a typical 3 by 2 matrix
        typ = Matrix(3, 2)
        typ.set_row(1, [3, 2])
        typ.set_row(2, [4, 1])
        typ.set_row(3, [5, 3])
        self.assertEqual((typ + zero_matrix), typ)
        self.assertEqual((zero_matrix + typ), typ)

    def test_standard_matrix_addition(self):
        # create a typical 3 by 2 matrix
        typ = Matrix(3, 2)
        typ.set_row(1, [3, 2])
        typ.set_row(2, [4, 1])
        typ.set_row(3, [5, 3])
        # create another 3 by 2 matrix that is populated by 1s
        populated_1 = Matrix(3, 2)
        populated_1.set_row(1, [1, 1])
        populated_1.set_row(2, [1, 1])
        populated_1.set_row(3, [1, 1])
        # create an expected matrix
        expected = Matrix(3, 2)
        expected.set_row(1, [4, 3])
        expected.set_row(2, [5, 2])
        expected.set_row(3, [6, 4])
        self.assertEqual((typ + populated_1), expected)
        self.assertEqual((populated_1 + typ), expected)

    def test_base_concatenate_addition(self):
        # create a typical 3 by 2 matrix
        typ = Matrix(3, 2)
        typ.set_row(1, [3, 2])
        typ.set_row(2, [4, 1])
        typ.set_row(3, [5, 3])
        # create a empty string matrix
        empty_str_mat = Matrix(3, 2)
        empty_str_mat.set_row(1, ['', ''])
        empty_str_mat.set_row(2, ['', ''])
        empty_str_mat.set_row(3, ['', ''])
        # create an expected matrix
        expected = Matrix(3, 2)
        expected.set_row(1, ['3', '2'])
        expected.set_row(2, ['4', '1'])
        expected.set_row(3, ['5', '3'])
        self.assertEqual((typ + empty_str_mat), expected)
        self.assertEqual((empty_str_mat + typ), expected)

    def test_concatenate_addition(self):
        # create an base mixed matrix
        base_mat = Matrix(3, 2)
        base_mat.set_row(1, [3, True])
        base_mat.set_row(2, [False, 'Hello'])
        base_mat.set_row(3, ['Bye', 3])
        # create an adding matrix
        adding = Matrix(3, 2)
        adding.set_row(1, [3, 2])
        adding.set_row(2, [4, 1])
        adding.set_row(3, [5, 3])
        # create an expected matrix
        expected = Matrix(3, 2)
        expected.set_row(1, ['33', 'True2'])
        expected.set_row(2, ['False4', 'Hello1'])
        expected.set_row(3, ['Bye5', '33'])
        self.assertEqual(base_mat + adding, expected)
        self.assertTrue((adding + base_mat) != expected)

    def test_unequal_size_addition(self):
        # create a typical 3 by 2 matrix
        typ = Matrix(3, 2)
        typ.set_row(1, [3, 2])
        typ.set_row(2, [4, 1])
        typ.set_row(3, [5, 3])
        # create another typical matrix
        typ2 = Matrix(2, 3)
        typ2.set_row(1, [1, 2, 3])
        typ2.set_row(2, [4, 5, 6])
        self.assertRaises(InvalidSizeException, typ.__add__, typ2)


class TestMatrixSubtraction(unittest.TestCase):

    def test_zero_matrix_subtraction(self):
        # create a 3 by 2 zero matrix
        zero_matrix = Matrix(3, 2)
        # create a generic matrix
        gen = Matrix(3, 2)
        gen.set_row(1, [3, 2])
        gen.set_row(2, [4, 1])
        gen.set_row(3, [5, 3])
        self.assertEqual((gen - zero_matrix), gen)
        self.assertTrue((zero_matrix - gen) != gen)

    def test_standard_matrix_subtraction(self):
        # create a generic matrix
        gen = Matrix(3, 2)
        gen.set_row(1, [3, 2])
        gen.set_row(2, [4, 1])
        gen.set_row(3, [5, 3])
        # create a matrix whose populated by all 1s
        populated_ones = Matrix(3, 2)
        populated_ones.set_row(1, [1, 1])
        populated_ones.set_row(2, [1, 1])
        populated_ones.set_row(3, [1, 1])
        # create an expected matrix
        expected = Matrix(3, 2)
        expected.set_row(1, [2, 1])
        expected.set_row(2, [3, 0])
        expected.set_row(3, [4, 2])
        self.assertEqual((gen - populated_ones), expected)
        self.assertTrue((populated_ones - gen) != expected)

    def test_cannot_concatenate_substraction(self):
        # create a generic matrix
        gen = Matrix(3, 2)
        gen.set_row(1, [3, 2])
        gen.set_row(2, [4, 1])
        gen.set_row(3, [5, 3])
        # create an empty string 3 by 2 matrix
        empty_str_mat = Matrix(3, 2)
        empty_str_mat.set_row(1, ['', ''])
        empty_str_mat.set_row(2, ['', ''])
        empty_str_mat.set_row(3, ['', ''])
        self.assertRaises(NotMathematicalException, gen.__sub__, empty_str_mat)

    def test_mixed_matrix_subtraction(self):
        # create a generic 3 by 2 matrix
        gen = Matrix(3, 2)
        gen.set_row(1, [3, 2])
        gen.set_row(2, [4, 1])
        gen.set_row(3, [5, 3])
        # create a mixed 3 by 2 matrix
        mixed = Matrix(3, 2)
        mixed.set_row(1, [3, 'bar'])
        mixed.set_row(2, ['foo', True])
        mixed.set_row(3, [False, 'hello'])
        self.assertRaises(NotMathematicalException, gen.__sub__, mixed)

    def test_unequal_size_subtraction(self):
        # create a 2 by 3 matrix
        base = Matrix(2, 3)
        base.set_row(1, [1, 2, 3])
        base.set_row(2, [4, 5, 6])
        # create a 3 by 2 matrix
        secon = Matrix(3, 2)
        secon.set_row(1, [2, 1])
        secon.set_row(2, [3, 0])
        secon.set_row(3, [4, 2])
        self.assertRaises(InvalidSizeException, base.__sub__, secon)


class TestMatrixMultiplication(unittest.TestCase):

    def test_rectangle_x_square_mul(self):
        # set up 2 by 2 identity matrix
        identity = Matrix(2, 2)
        identity.set_row(1, [1, 0])
        identity.set_row(2, [0, 1])
        # set up a 3 by 2 matrix
        long_matrix = Matrix(3, 2)
        long_matrix.set_row(1, [1, 4])
        long_matrix.set_row(2, [2, 5])
        long_matrix.set_row(3, [3, 6])
        self.assertEqual((long_matrix * identity), long_matrix)

    def test_square_x_rectangle_mul(self):
        # set up 2 by 2 identity matrix
        identity = Matrix(2, 2)
        identity.set_row(1, [1, 0])
        identity.set_row(2, [0, 1])
        # set up a 2 by 3 matrix
        wide_matrix = Matrix(2, 3)
        wide_matrix.set_row(1, [7, 8, 9])
        wide_matrix.set_row(2, [10, 11, 12])
        self.assertEqual((identity * wide), wide)

    def test_order_matters_mul(self):
        # set up 2 by 2 identity matrix
        identity = Matrix(2, 2)
        identity.set_row(1, [1, 0])
        identity.set_row(2, [0, 1])
        # set up a 3 by 2 matrix
        long_matrix = Matrix(3, 2)
        long_matrix.set_row(1, [1, 4])
        long_matrix.set_row(2, [2, 5])
        long_matrix.set_row(3, [3, 6])
        self.assertRaises(UnequalInnerProductException, identity.__mul__,
                          long_matrix)

    def test_non_matrix_mul(self):
        # set up a 3 by 2 non mathematical matrix
        not_math_mat = Matrix(3, 2)
        not_math_mat.set_row = (1, ['I walk in a', 'bar'])
        not_math_mat.set_row = (2, ['foo', 1])
        not_math_mat.set_row = (3, [4.0, 'bar'])
        # set up a 2 by 3 matrix
        wide_matrix = Matrix(2, 3)
        wide_matrix.set_row(1, [7, 8, 9])
        wide_matrix.set_row(2, [10, 11, 12])
        self.assertRaises(NotMathematicalException, not_math_mat.__mul__,
                          wide_matrix)
        self.assertRaises(NotMathematicalException, wide_matrix.__mul__,
                          not_math_mat)

    def test_standard_matrix_mul(self):
        # set up a 2 by 3 matrix
        wide_matrix = Matrix(2, 3)
        wide_matrix.set_row(1, [7, 8, 9])
        wide_matrix.set_row(2, [10, 11, 12])
        # set up a 3 by 2 matrix
        long_matrix = Matrix(3, 2)
        long_matrix.set_row(1, [1, 4])
        long_matrix.set_row(2, [2, 5])
        long_matrix.set_row(3, [3, 6])
        # set up an expected matrix
        expected = Matrix(2, 2)
        expected.set_row(1, [50, 122])
        expected.set_row(1, [68, 167])
        self.assertEqual((wide_matrix * long_matrix), expected)

    def test_type_square_matrix_from_mul(self):
        # set up a 2 by 3 matrix
        wide_matrix = Matrix(2, 3)
        wide_matrix.set_row(1, [7, 8, 9])
        wide_matrix.set_row(2, [10, 11, 12])
        # set up a 3 by 2 matrix
        long_matrix = Matrix(3, 2)
        long_matrix.set_row(1, [1, 4])
        long_matrix.set_row(2, [2, 5])
        long_matrix.set_row(3, [3, 6])
        new_matrix = wide_matrix * long_matrix
        self.assertTrue(isinstance(new_matrix, SquareMatrix))
        self.assertTrue(isinstance(new_matrix, Matrix))

    def test_type_one_dimensional_matrix_from_mul(self):
        # set up a 1 by 2 matrix
        base_matrix = Matrix(1, 2)
        base_matrix.set_row(1, [1, 2])
        # set up a 2 by 3 matrix
        wide_matrix = Matrix(2, 3)
        wide_matrix.set_row(1, [1, 1, 1])
        wide_matrix.set_row(2, [1, 1, 1])
        new_matrix = base_matrix * wide_matrix
        self.assertTrue(isinstance(new_matrix, OneDimensionalMatrix))
        self.assertTrue(isinstance(new_matrix, Matrix))

    def test_type_one_element_matrix_from_mul(self):
        # set up a 1 by 2 matrix
        base_matrix = Matrix(1, 2)
        base_matrix.set_row(1, [1, 2])
        # set up a 2 by 1 matrix
        long_matrix = Matrix(2, 1)
        long_matrix.set_row(1, [1])
        long_matrix.set_row(2, [1])
        new_matrix = base_matrix * long_matrix
        self.assertTrue(isinstance(new_matrix, OneElementMatrix))
        self.assertTrue(isinstance(new_matrix, SquareMatrix))
        self.assertTrue(isinstance(new_matrix, OneDimensionalMatrix))
        self.assertTrue(isinstance(new_matrix, Matrix))


class TestEqual(unittest.TestCase):

    def test_initialized_matrices(self):
        base_1 = Matrix(3, 3)
        base_2 = Matrix(3, 3)
        expected = base_1 == base_2
        self.assertTrue(expected)

    def test_unequal_size_matrices(self):
        basic_1 = Matrix(3, 4)
        basic_2 = Matrix(4, 3)
        expected = basic_1 != basic_2
        self.assertTrue(expected)

    def test_unequal_elements(self):
        mat_1 = Matrix(3, 3)
        mat_2 = Matrix(3, 3)
        mat_2.set_row(1, [1, 1])
        expected = mat_1 != mat_2
        self.assertTrue(expected)

    def test_mixed_elements(self):
        mat_1 = Matrix(2, 2)
        mat_2 = Matrix(2, 2)
        mat_1.set_row(1, ['None', True])
        mat_2.set_row(1, ['None', True])
        mat_1.set_row(2, [2, False])
        mat_2.set_row(2, [2, False])
        expected = mat_1 == mat_2
        self.assertTrue(expected)

    def test_really_unequal_matrices(self):
        mat_1 = Matrix(3, 2)
        mat_2 = Matrix(2, 3)
        mat_1.set_row(1, [None, False])
        mat_1.set_row(2, [True, 500])
        mat_1.set_row(3, ['stop', 'go'])
        mat_2.set_row(1, [200, 200, 200])
        mat_2.set_row(2, [400, 400, 400])
        expected = mat_1 != mat_2
        self.assertTrue(expected)

    def test_identical_matrices(self):
        mat_1 = Matrix(2, 2)
        mat_2 = Matrix(2, 2)
        mat_1.set_row(1, [1, 0])
        mat_2.set_row(1, [1, 0])
        mat_1.set_row(2, [0, 1])
        mat_2.set_row(2, [0, 1])
        self.assertEqual(mat_1, mat_2)

# Matrix.__str__ does require some degree of implementation...
# so it won't be necessarily focused on


class TestStr(unittest.TestCase):

    def test_initialized_matrix(self):
        some_mat = Matrix(2, 2)
        our_str = some_mat.__str__()
        self.assertTrue(isinstance(our_str, str))


class TestGetSize(unittest.TestCase):

    def test_generic_matrix(self):
        mat_1 = Matrix(4, 3)
        rows, col = Matrix.get_size()
        self.assertTrue(4 == rows and 3 == col)


class TestGetCol(unittest.TestCase):

    def test_initialized_matrix(self):
        mat_1 = Matrix(3, 5)
        expected = [0, 0, 0]
        num_cols = mat_1.get_size()[1]
        # just go through each column, expecting its all 0 elements
        for i in range(1, num_cols + 1):
            self.assertEqual(mat_1.get_col(i), expected)

    def test_row_changer(self):
        mat_1 = Matrix(3, 5)
        # Each column in as a list starts with 'i' in a for loop
        mat_1.set_row(1, [1, 2, 3, 4, 5])
        num_cols = mat_1.get_size()[1]  # set at 5, remember theres 3 rows...
        for i in range(1, num_cols + 1):
            self.assertEqual(mat_1.get_col(i), [i, 0, 0])

    def test_invalid_col_index_over(self):
        mat_1 = Matrix(3, 5)
        self.assertRaises(InvalidColumnIndexException, mat_1.get_col, 6)

    def test_invalid_col_index_under(self):
        mat_1 = Matrix(3, 5)
        self.assertRaises(InvalidColumnIndexException, mat_1.get_col, 0)


class TestSetCol(unittest.TestCase):

    def test_row_change(self):
        mat_1 = Matrix(3, 3)
        num_col = mat_1.get_size()[1]
        for i in range(1, num_col + 1):
            mat_1.set_col(i, [i, 0, 0])  # top row will be [1, 2, 3]
        expected = [1, 2, 3]
        got = mat_1.get_row(1)
        self.assertEqual(expected, got)

    def test_rows_change(self):
        mat_1 = Matrix(4, 3)  # 4 rows, 3 columns
        num_row, num_col = mat_1.get_size()
        # set each column by the index 'i'
        for i in range(1, num_col + 1):
            mat_1.set_col(i, [i, i * 2, i * 3, i * 4])
        # None is set as the placeholder, all else is expected for each row
        expectation = [None, [1, 2, 3], [2, 4, 6], [3, 6, 9], [4, 8, 12]]
        for i in range(1, num_row + 1):
            self.assertEqual(expectation[i], mat_1.get_row(i))

    def test_invalid_list_for_col_under(self):
        mat_1 = Matrix(3, 4)
        inserter = [1, 1]
        self.assertRaises(InsufficientElementsException, mat_1.set_col, 1,
                          inserter)

    def test_invalid_list_for_col_over(self):
        mat_1 = Matrix(3, 4)
        inserter = [1, 1, 1, 1]
        self.assertRaises(ExcessElementsException, mat_1.set_row, 1,
                          inserter)

    def test_invalid_col_index_over(self):
        mat_1 = Matrix(3, 4)
        inserter = [1, 1, 1]
        self.assertRaises(InvalidColumnIndexException, mat_1.set_col, 5,
                          inserter)

    def test_invalid_col_index_under(self):
        mat_1 = Matrix(3, 4)
        inserter = [1, 1, 1]
        self.assertRaises(InvalidColumnIndexException, mat_1.set_col, 0,
                          inserter)


class TestGetRow(unittest.TestCase):

    def test_initial_matrix(self):
        mat_1 = Matrix(3, 5)
        num_rows = mat_1.get_size()[0]
        expected = [0, 0, 0, 0, 0]
        # assuming all entries are 0s.
        for i in range(1, num_rows):
            self.assertEqual(mat_1.get_row(i), expected)

    def test_basic_matrix(self):
        mat_1 = Matrix(3, 2)
        mat_1.set_col(1, [1, 2, 3])
        mat_1.set_col(2, [4, 5, 6])
        num_rows = mat_1.get_size()[0]
        # each row is two entries, we're testing the get row here...
        for i in range(1, num_rows + 1):
            self.assertEqual(mat_1.get_row(i), [i, i + 3])

    def test_invalid_index_under(self):
        mat_1 = Matrix(3, 5)
        self.assertRaises(InvalidRowIndexException, mat_1.get_row, 0)

    def test_invalid_index_over(self):
        mat_1 = Matrix(3, 5)
        self.assertRaises(InvalidRowIndexException, mat_1.get_row, 4)


class TestSetRow(unittest.TestCase):
    # A lot of set_row cases were used throughout this script so this is just
    # touching upon the raising exception cases

    def setUp(self):
        self._gen_matrix = Matrix(4, 6)
        self._row = [0, 0, 0, 0, 0, 0]
        self._under = [0]
        self._over = [0, 0, 0, 0, 0, 0, 0]

    def test_invalid_row_index_under(self):
        self.assertRaises(InvalidRowIndexException, self._gen_matrix.set_row,
                          0, self._row)

    def test_invalid_row_index_over(self):
        self.assertRaises(InvalidRowIndexException, self._gen_matrix.set_row,
                          5, self._row)

    def test_invalid_list_under(self):
        self.assertRaises(InsufficientElementsException,
                          self._gen_matrix.set_row, 1, self._under)

    def test_invalid_list_over(self):
        self.assertRaises(ExcessElementsException, self._gen_matrix.set_row,
                          1, self._over)


class TestGetValue(unittest.TestCase):

    def test_initialized_matrix(self):
        mat_1 = Matrix(3, 3)
        self.assertEqual(mat_1.get_value(1, 1), 0)

    def test_set_row_matrix(self):
        mat_1 = Matrix(3, 3)
        mat_1.set_row(2, [0, 'hello', 0])
        self.assertEqual(mat_1.get_value(2, 2), 'hello')

    def test_invalid_row_over(self):
        mat_1 = Matrix(3, 3)
        self.assertRaises(InvalidRowIndexException, mat_1.get_value, 4, 3)

    def test_invalid_row_under(self):
        mat_1 = Matrix(3, 3)
        self.assertRaises(InvalidRowIndexException, mat_1.get_value, 0, 3)

    def test_invalid_col_over(self):
        mat_1 = Matrix(3, 3)
        self.assertRaises(InvalidColumnIndexException, mat_1.get_value, 3, 4)

    def test_invalid_col_under(self):
        mat_1 = Matrix(3, 3)
        self.assertRaises(InvalidColumnIndexException, mat_1.get_value, 3, 0)


class TestSetValue(unittest.TestCase):

    def test_one_change(self):
        mat_1 = Matrix(2, 2)
        mat_1.set_value(1, 1, 'None')  # row, column, value
        self.assertEqual(mat_1.get_value(1, 1), 'None')

    def test_horrible_identity(self):
        mat_1 = Matrix(20, 20)
        num_row, num_col = mat_1.get_size()
        # the code below is actually making an identity matrix for mat_1
        for i in range(1, num_row + 1):
            for j in range(1, num_col + 1):
                if (i == j):
                    mat_1.set_value(i, j, 1)

        for i in range(1, num_row + 1):
            for j in range(1, num_col + 1):
                if (i == j):
                    # just check the diagonal
                    self.assertEqual(mat_1.get_value(i, j), 1)

    def test_invalid_row_index_under(self):
        mat_1 = Matrix(2, 2)
        self.assertRaises(InvalidRowIndexException, mat_1.set_value, 0, 2, 1)

    def test_invalid_row_index_over(self):
        mat_1 = Matrix(2, 2)
        self.assertRaises(InvalidRowIndexException, mat_1.set_value, 3, 2, 1)

    def test_invalid_col_index_under(self):
        mat_1 = Matrix(2, 2)
        self.assertRaises(InvalidRowIndexException, mat_1.set_value, 1, 0, 1)

    def test_invalid_col_index_over(self):
        mat_1 = Matrix(2, 2)
        self.assertRaises(InvalidRowIndexException, mat_1.set_value, 1, 3, 1)


class TestSwapCol(unittest.TestCase):

    def test_standard_matrix(self):
        mat = Matrix(3, 3)
        mat.set_row(1, [1, 2, 3])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [7, 8, 9])
        mat.swap_col(1, 3)

        mat_2 = Matrix(3, 3)
        mat_2.set_row(1, [3, 2, 1])
        mat_2.set_row(2, [6, 5, 2])
        mat_2.set_row(3, [9, 8, 3])
        self.assertEqual(mat, mat_2)

    def test_invalid_col_index_under(self):
        mat = Matrix(3, 3)
        mat.set_row(1, [1, 2, 3])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [7, 8, 9])
        self.assertRaises(InvalidColumnIndexException, mat.swap_col, 0, 3)

    def test_invalid_col_index_over(self):
        mat = Matrix(3, 3)
        mat.set_row(1, [1, 2, 3])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [7, 8, 9])
        self.assertRaises(InvalidColumnIndexException, mat.swap_col, 2, 4)


class TestSwapRow(unittest.TestCase):

    def test_standard_matrix(self):
        mat = Matrix(3, 3)
        mat.set_row(1, [1, 2, 3])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [7, 8, 9])
        mat.swap_row(1, 3)

        mat_2 = Matrix(3, 3)
        mat.set_row(1, [7, 8, 9])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [1, 2, 3])
        self.assertEqual(mat, mat_2)

    def test_invalid_row_index_under(self):
        mat = Matrix(3, 3)
        mat.set_row(1, [1, 2, 3])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [7, 8, 9])
        self.assertRaises(InvalidRowIndexException, mat.swap_row, 0, 3)

    def test_invalid_row_index_over(self):
        mat = Matrix(3, 3)
        mat.set_row(1, [1, 2, 3])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [7, 8, 9])
        self.assertRaises(InvalidRowIndexException, mat.swap_row, 2, 4)


class TestIsMathematicalMatrix(unittest.TestCase):

    def test_initialized_matrix(self):
        mat = Matrix(3, 3)
        self.assertTrue(mat.is_mathematical_matrix())

    def test_populated_matrix(self):
        mat = Matrix(3, 3)
        mat.set_row(1, [1, 2, 3])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [7, 8, 9])
        self.assertTrue(mat.is_mathematical_matrix())

    def test_string_matrix(self):
        mat = Matrix(3, 3)
        mat.set_row(1, ['1', '2', '3'])
        mat.set_row(2, ['4', '5', '6'])
        mat.set_row(3, ['7', '8', '9'])
        self.assertTrue(not mat.is_mathematical_matrix())

    def test_mixed_matrix(self):
        mat = Matrix(3, 3)
        mat.set_row(1, [1, '2', None])
        mat.set_row(2, ['4', 5, '6'])
        mat.set_row(3, [7, '8', 9])
        self.assertTrue(not mat.is_mathematical_matrix())


class TestTranspose(unittest.TestCase):

    def test_initialized_matrices(self):
        mat = Matrix(3, 2)
        another_mat = Matrix(2, 3)
        self.assertEqual(mat.transpose(), another_mat)

    def test_populated_matrices(self):
        mat = Matrix(3, 2)
        mat.set_row(1, [1, 4])
        mat.set_row(2, [2, 5])
        mat.set_row(3, [3, 6])

        another_mat = Matrix(2, 3)
        another_mat.set_row(1, [1, 2, 3])
        another_mat.set_row(2, [4, 5, 6])

        self.assertEqual(mat.transpose(), another_mat)

    def test_horiz_and_vertical_matrices(self):
        mat = Matrix(3, 1)
        mat.set_col(1, ['1', 2, 'hello'])

        another_mat = Matrix(1, 3)
        another_mat.set_row(1, ['1', 2, 'hello'])

        self.assertEqual(mat.transpose(), another_mat)


class TestMulScalar(unittest.TestCase):

    def test_all_8s(self):
        mat = Matrix(2, 3)
        another_mat = Matrix(2, 3)
        num_rows, num_cols = mat.get_size()

        # set all elements for mat to be 1
        # set all elements for another_mat to be 8
        for i in range(1, num_rows + 1):
            for j in range(1, num_rows + 1):
                mat.set_value(i, j, 1)
                another_mat.set_value(i, j, 8)
        # return the matrix with mul_scalar
        self.assertEqual(mat.mul_scalar(8), another_mat)

    def test_non_math_matrix(self):
        mat = matrix(2, 3)
        mat.set_row(1, ['1', 2, 3])
        mat.set_row(2, [4, 5, 6])
        self.assertRaise(NotMathematicalException, math.mul_scalar, 8)


class TestInitialzeSquareMatrix(unittest.TestCase):

    def setUp(self):
        self._base = SquareMatrix(2)
        self._sym = SquareMatrix(2, True)
        self._one_ele = SquareMatrix(1)

    def test_is_equal(self):
        self.assertEqual(self._base, self._sym)

    def test_has_symmetric_properties(self):
        self.assertTrue(self._sym.has_symmetric_properties())
        self.assertTrue(not self._base.has_symmetric_properties())
        self.assertTrue(self._one_ele.has_symmetric_properties())

    def test_one_element_parents(self):
        self.assertTrue(isinstance(self._one_ele, OneElementMatrix))
        self.assertTrue(isinstance(self._one_ele, OneDimensionalMatrix))
        self.assertTrue(isinstance(self._one_ele, SquareMatrix))
        self.assertTrue(isinstance(self._one_ele, Matrix))

    def test_zero_int_populator(self):
        for i in range(1, 3):
            self.assertEqual(self._base.get_row(i), [0, 0])
            self.assertEqual(self._sym.get_row(i), [0, 0])
        self.assertEqual(self._one_ele.get_value(1, 1), 0)


class TestIdentityMe(unittest.TestCase):

    def test_default_identity(self):
        basic_sq = SquareMatrix(2)
        basic_r2 = SquareMatrix(2)
        basic_sq.identity_me()
        basic_r2.set_row(1, [1, 0])
        basic_r2.set_row(2, [0, 1])
        self.assertEqual(basic_sq, basic_r2)

    def test_obj_identity(self):
        basic_sq = SquareMatrix(2)
        basic_r2 = SquareMatrix(2)
        basic_sq.identity_me('7')
        basic_r2.set_row(1, ['7', 0])
        basic_r2.set_row(2, [0, '7'])
        self.assertEqual(basic_sq, basic_r2)


class TestGetDiagonal(unittest.TestCase):

    def test_generic_identity(self):
        basic_sq = SquareMatrix(2)
        basic_sq.identity_me('7')
        expected = ['7', '7']
        self.assertEqual(basic_sq.get_diagonal(), expected)

    def test_not_identity(self):
        basic_sq = SquareMatrix(3)
        basic_sq.set_row(1, [44, 3, 22])
        basic_sq.set_row(2, [9, 5, 2])
        basic_sq.set_row(3, [23, 82, 8])
        expected = [44, 5, 8]
        self.assertEqual(basic_sq.get_diagonal(), expected)


class TestSetDiagonal(unittest.TestCase):

    def test_create_identity(self):
        basic_sq = SquareMatrix(2)
        basic_sq.identity_me('7')
        new_sq = SquareMatrix(2)
        new_sq.set_diagonal(['7', '7'])
        self.assertEqual(basic_sq, new_sq)

    def test_not_identity(self):
        basic_sq = SquareMatrix(2)
        basic_sq.set_row(1, [3, 2])
        basic_sq.set_row(2, ['1265', 'Military Trail'])
        # create another square matrix
        new_sq = SquareMatrix(2)
        new_sq.set_value(1, 2, 2)
        new_sq.set_value(2, 1, '1265')
        # populate the diagonal
        new_sq.set_diagonal([3, 'Military Trail'])
        self.assertEqual(basic_sq, new_sq)

    def test_insufficient_for_diagonal(self):
        basic_sq = SquareMatrix(2)
        self.assertRasies(InsufficientElementsException, basic_sq.set_diagonal,
                          [1])

    def test_excess_for_diagonal(self):
        basic_sq = SquareMatrix(2)
        self.assertRasies(ExcessElementsException, basic_sq.set_diagonal,
                          [2, 2, 2, 2, 2, 2])


class TestDeterminant(unittest.TestCase):

    def test_random_2_side_matrix(self):
        basic_sq = SquareMatrix(2)
        basic_sq.set_row(1, [5, 3])
        basic_sq.set_row(2, [-1, 4])
        expected = 23
        self.assertEqual(basic_sq.determinant(), expected)

    def test_bad_size_det(self):
        basic_sq = SquareMatrix(3)
        basic.set_row(1, [1, 2, 3])
        basic.set_row(2, [4, 5, 6])
        basic.set_row(3, [7, 8, 9])
        self.assertRaises(InvalidSizeException, basic_sq.determinant)

    def test_not_mathematical_det(self):
        basic_sq = SquareMatrix(2)
        basic_sq.set_row(1, ['5', 3])
        basic_sq.set_row(2, [-1, 4])
        self.assertRaises(NotMathematicalException, basic_sq.determinant)


class TestSquareSetValue(unittest.TestCase):

    def test_generic_square_matrix(self):
        basic_sq = SquareMatrix(2)
        basic_sq.set_row(1, [2, 3])
        basic_sq.set_row(2, [4, 5])
        basic_sq.set_value(2, 2, '44 hellos')
        expected = '44 hellos'
        self.assertEqual(basic_sq.get_value(2, 2), expected)

    def test_symmetric_matrix(self):
        sym_sq = SquareMatrix(3, True)
        sym_sq.identity_me('hi')
        sym_sq.set_value(3, 1, 'hello')
        expected = 'hello'
        self.assertEqual(basic_sq.get_value(1, 3), expected)

    def test_invalid_row_index_under(self):
        basic_sq = SquareMatrix(2)
        self.assertRaises(InvalidRowIndexException, basic_sq.set_value, 0, 1,
                          1)

    def test_invalid_row_index_over(self):
        basic_sq = SquareMatrix(2)
        self.assertRaises(InvalidRowIndexException, basic_sq.set_value, 3, 2,
                          5)

    def test_invalid_col_index_under(self):
        basic_sq = SquareMatrix(3)
        self.assertRaises(InvalidColumnIndexException, basic_sq.set_value, 1,
                          0, 23)

    def test_invalid_col_index_over(self):
        basic_sq = SquareMatrix(4)
        self.assertRaises(InvalidColumnIndexException, basic_sq.set_value, 2,
                          5, 99)


class TestSquareSetRow(unittest.TestCase):

    def test_basic_square(self):
        sq = SquareMatrix(2)
        sq.set_row(1, [1, 4])
        sq.set_row(2, [2, 3])
        self.assertEqual(sq.get_row(1), [1, 4])
        self.assertEqual(sq.get_row(2), [2, 3])

    def test_symmetric_square(self):
        sym_sq = SquareMatrix(3, True)
        sym_sq.set_row(1, [1, 2, 3])
        sym_sq.set_row(2, [4, 5, 6])
        sym_sq.set_row(3, [7, 8, 9])  # each row just laps onto each other
        self.assertTrue(sq.get_row(1) == [1, 4, 7] == sq.get_col(1))
        self.assertTrue(sq.get_row(2) == [4, 5, 8] == sq.get_col(2))
        self.assertTrue(sq.get_row(3) == [7, 8, 9] == sq.get_col(3))

    def test_invalid_row_index_under(self):
        sq = SquareMatrix(2)
        self.assertRaise(InvalidRowIndexException, sq.set_row, 0, [1, 1])

    def test_invalid_row_index_over(self):
        sq = SquareMatrix(2)
        self.assertRaise(InvalidRowIndexException, sq.set_row, 3, [23, 91])

    def test_insufficient_elements(self):
        sq = SquareMatrix(2, True)
        self.assertRaise(InsufficientElementsException, sq.set_row, 2, [1])

    def test_excess_elements(self):
        sq = SquareMatrix(3, True)
        self.assertRaise(InsufficientElementsException, sq.set_row, 2,
                         [1, 3, 5, 7])


class TestSquareSetCol(unittest.TestCase):

    def test_basic_matrix(self):
        sq = SquareMatrix(2)
        sq.set_col(1, [1, 2, 3])
        sq.set_col(2, [4, 5, 6])
        sq.set_col(3, [7, 8, 9])
        self.assertEqual(sq.get_row(1), [1, 4, 7])
        self.assertEqual(sq.get_row(2), [2, 5, 8])
        self.assertEqual(sq.get_row(3), [3, 6, 9])

    def test_symmetric_matrix(self):
        sym_sq = SquareMatrix(3, True)
        sym_sq.set_col(1, [22, 9, 5])
        sym_sq.set_col(2, [53, '7', 1])
        sym_sq.set_col(3, ['hello', 8, True])
        self.assertTrue(sq.get_row(1) == [22, 53, 'hello'] == sq.get_col(1))
        self.assertTrue(sq.get_row(2) == [53, '7', 8] == sq.get_col(2))
        self.assertTrue(sq.get_row(3) == ['hello', 8, True] == sq.get_col(3))

    def test_invalid_col_index_under(self):
        sq = SquareMatrix(4)
        self.assertRaises(InvalidColumnIndexException, sq.set_col, -1,
                          [1, 2, 3, 4])

    def test_invalid_col_index_over(self):
        sq = SquareMatrix(4)
        self.assertRaises(InvalidColumnIndexException, sq.set_col, 5,
                          [1, 2, 3, 4])

    def test_insufficient_elements(self):
        sq = SquareMatrix(3)
        self.assertRaises(InsufficientElementsException, sq.set_col, 1,
                          [1, 2])

    def test_excess_elements(self):
        sq = SquareMatrix(3)
        self.assertRaises(InsufficientElementsException, sq.set_col, 3,
                          [1, 2, 3, 4, 5])


class TestSquareSwapCol(unittest.TestCase):

    def test_standard_matrix(self):
        mat = SquareMatrix(3)
        mat.set_row(1, [1, 2, 3])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [7, 8, 9])
        mat.swap_col(1, 3)

        mat_2 = SquareMatrix(3)
        mat_2.set_row(1, [3, 2, 1])
        mat_2.set_row(2, [6, 5, 4])
        mat_2.set_row(3, [9, 8, 7])
        self.assertEqual(mat, mat_2)

    def test_symmetric_matrix(self):
        sym = SquareMatrix(3, True)
        sym.set_row(1, [1, 2, 3])
        sym.set_row(2, [4, 5, 6])
        sym.set_row(3, [7, 8, 9])
        sym.swap_col(1, 3)
        expected = SquareMatrix(3)
        expected.set_row(1, [9, 8, 7])
        expected.set_row(2, [8, 5, 4])
        expected.set_row(3, [7, 4, 1])
        self.assertEqual(sym, expected)

    def test_invalid_col_index_under(self):
        mat = SquareMatrix(3)
        mat.set_row(1, [1, 2, 3])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [7, 8, 9])
        self.assertRaises(InvalidColumnIndexException, mat.swap_col, 0, 3)

    def test_invalid_col_index_over(self):
        mat = SquareMatrix(3)
        mat.set_row(1, [1, 2, 3])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [7, 8, 9])
        self.assertRaises(InvalidColumnIndexException, mat.swap_col, 2, 4)


class TestSquareSwapRow(unittest.TestCase):

    def test_standard_matrix(self):
        mat = SquareMatrix(3)
        mat.set_row(1, [1, 2, 3])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [7, 8, 9])
        mat.swap_row(1, 3)

        mat_2 = SquareMatrix(3)
        mat.set_row(1, [7, 8, 9])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [1, 2, 3])
        self.assertEqual(mat, mat_2)

    def test_sym_matrix(self):
        sym = SquareMatrix(3, True)
        sym.set_row(1, [1, 2, 3])
        sym.set_row(2, [4, 5, 6])
        sym.set_row(3, [7, 8, 9])
        sym.swap_row(1, 3)
        expected = SquareMatrix(3)
        expected.set_row(1, [9, 8, 7])
        expected.set_row(2, [8, 5, 4])
        expected.set_row(3, [7, 4, 1])
        self.assertEqual(sym, expected)

    def test_invalid_row_index_under(self):
        mat = SquareMatrix(3)
        mat.set_row(1, [1, 2, 3])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [7, 8, 9])
        self.assertRaises(InvalidRowIndexException, mat.swap_row, 0, 3)

    def test_invalid_row_index_over(self):
        mat = SquareMatrix(3)
        mat.set_row(1, [1, 2, 3])
        mat.set_row(2, [4, 5, 6])
        mat.set_row(3, [7, 8, 9])
        self.assertRaises(InvalidRowIndexException, mat.swap_row, 2, 4)


class TestSymmetricProperties(unittest.TestCase):

    def test_non_symmetric(self):
        sq = SquareMatrix(3)
        self.assertTrue(not sq.has_symmetric_properties())

    def test_symmetric(self):
        sym = SquareMatrix(3, True)
        self.assertTrue(sym.has_symmetric_properties())

# now test the one dimensional matrices


class TestOneDimensionalInitialize(unittest.TestCase):

    def test_horizontal(self):
        dim_mat = OneDimensionalMatrix(7)
        self.assertTrue(dim_mat.is_horizontal())

    def test_vertical(self):
        dim_mat = OneDimensionalMatrix(7, False)
        self.assertTrue(dim_mat.is_vertical())

    def test_zero_populated_elements(self):
        dim_mat = OneDimensionalMatrix(4)
        self.assertEqual(dim_mat.get_all(), [0, 0, 0, 0])

    def test_one_element_matrix(self):
        dim_mat = OneDimensionalMatrix(1)
        self.assertTrue(isinstance(dim_mat, OneElementMatrix))
        self.assertTrue(isinstance(dim_mat, SquareMatrix))
        self.assertTrue(isinstance(dim_mat, Matrix))


class TestOneDimensionalSetAndGetEle(unittest.TestCase):

    def test_set_ele_in_range(self):
        dim_mat = OneDimensionalMatrix(7)
        dim_mat.set_ele(7, 3)
        self.assertEqual(dim_mat.get_value(1, 7), 3)

    def test_get_ele_in_range(self):
        dim_mat = OneDimensionalMatrix(2)
        dim_mat.set_ele(2, 'hi')
        self.assertEqual(dim_mat.get_ele(2), 'hi')

    def test_set_ele_under_range(self):
        dim_mat = OneDimensionalMatrix(5)
        self.assertRaises(InvalidIndexException, dim_mat.set_ele, 0, 3)

    def test_set_ele_over_range(self):
        dim_mat = OneDimensionalMatrix(5)
        self.assertRaises(InvalidIndexException, dim_mat.set_ele, 6, 'No')

    def test_get_ele_under_range(self):
        dim_mat = OneDimensionalMatrix(3)
        self.assertRaises(InvalidIndexException, dim_mat.get_ele, 0)

    def test_get_ele_over_range(self):
        dim_mat = OneDimensionalMatrix(11)
        self.assertTraises(InvalidIndexException, dim_mat.get_ele, 12)


class TestSetAllAndGetAll(unittest.TestCase):

    def test_get_and_set_all_in_range(self):
        dim_mat = OneDimensionalMatrix(5)
        expected = ['hi', True, None, 5, 'bye']
        dim_mat.set_all(expected)
        self.assertEqual(dim_mat.get_all(), expected)

    def test_set_all_insufficient(self):
        dim_mat = OneDimensionalMatrix(3, False)
        self.assertRaises(InsufficientElementsException, dim_mat.set_all,
                          [0])

    def test_set_all_excess(self):
        dim_mat = OneDimensionalMatrix(3)
        self.assertRaises(ExcessElementsException, dim_mat.set_all,
                          [0, 1, 2, 3, 4, 5])


class TestSwapping(unittest.TestCase):

    def test_basic_one_dim(self):
        dim_mat = OneDimensionalMatrix(5)
        setter = ['hello', 1, 2, 3, 'door']
        dim_mat.set_all(setter)
        dim_mat.swap(1, 4)
        expected = [3, 1, 2, 'hello', 'door']
        self.assertEqual(dim_mat.get_all(), expected)

    def test_swap_index_over_range(self):
        dim_mat = OneDimensionalMatrix(5)
        setter = ['hello', 1, 2, 3, 'door']
        self.assertRaises(InvalidIndexException, dim_mat.swap, 6, 2)

    def test_swap_index_under_range(self):
        dim_mat = OneDimensionalMatrix(5)
        setter = ['hello', 1, 2, 3, 'door']
        self.assertRaises(InvalidIndexException, dim_mat.swap, 5, -20)

# Now test the one element matrix


class TestInitializedOneEle(unittest.TestCase):

    def setUp(self):
        self._base = OneElementMatrix()

    def test_element_is_zero(self):
        self.assertEqual(self._base.get_value(1, 1), 0)

    def test_matrix_is_horizontal(self):
        self.assertTrue(self._base.is_horizontal())

    def test_matrix_is_vertical(self):
        self.assertTrue(self._base.is_vertical())

    def test_matrix_has_symmetrical_properties(self):
        self.assertTrue(self._base.has_symmetric_properties())


class TestSetMe(unittest.TestCase):

    def test_set_element_true(self):
        one_ele = OneElementMatrix()
        one_ele.set_me(True)
        self.assertTrue(one_ele.get_value(1, 1))

    def test_set_element_int(self):
        one_ele = OneElementMatrix()
        one_ele.set_me(54)
        expected = 54
        self.assertEqual(one_ele.get_value(1, 1), expected)


class TestGetMe(unittest.TestCase):

    def test_get_element_after_set_True(self):
        one_ele = OneElementMatrix()
        one_ele.set_value(1, 1, True)
        self.assertTrue(one_ele.get_me())

    def test_get_element_after_set(self):
        one_ele = OneElementMatrix()
        one_ele.set_value(1, 1, 58)
        expected = 58
        self.assertEqual(one_ele.get_me(), expected)

if __name__ == '__main__':
    unittest.main(exit=False)
