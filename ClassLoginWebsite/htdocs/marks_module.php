<?php
  $sql = "SELECT * ";
  $sql = $sql . "FROM student_marks ";
  $sql = $sql . "WHERE mat_type='" . $content. "'";
  $result = $conn->query($sql);
  if ($result->num_rows > 0){
    echo "<div class=\"material_title\">" . $content . " Marks</div>";
    // Below is a table, so its in a container so that we can center it.
    echo "<div class=\"push_marks\">";

      // we want to show the headings
      // each "row" is a listed item
      // within each row is another list, which entries are listed items
      echo "<ul id=\"marks_container\">";
        echo "<li class=\"row\">";
          echo "<ul class=\"mark\">";
            // each heading is showing their UTORid, Material, what mark they got, and the weight
            echo "<span class=\"mark_restrict mark_title utorid\"><li>UTORid</li></span>";
            echo "<span class=\"mark_restrict mark_title\"><li>First Name</li></span>";
            echo "<span class=\"mark_restrict mark_title\"><li>Last Name</li></span>";
            echo "<span class=\"mark_restrict mark_title\"><li>Material</li></span>";
            echo "<span class=\"mark_restrict mark_title\"><li>Mark</li></span>";
            echo "<span class=\"mark_restrict mark_title\"><li>Out of</li></span>";
            echo "<span class=\"mark_restrict mark_title weight\"><li>Weight</li></span>";
          echo "</ul>";
        echo "</li>";

        $total = 0;
        $num_people = 0;
        while($row = $result->fetch_assoc()) {
          // define the utorid, material type and mark... material type is already defined
          // as contents
          $utorid = $row["utorid"];
          $mark = $row["mark"];
          // now we can show what the mark is out of, and its weight
          // so we have to run another sql command
          $sql = "SELECT * ";
          $sql = $sql . "FROM course_materials ";
          $sql = $sql . "WHERE mat_type='" . $content . "'";
          $for_numbers = $conn->query($sql);  // different variable otherwise will crash
          $mat = $for_numbers->fetch_assoc();
          $out_of = $mat["out_of"];
          $weight = $mat["weight"];
          // and we want to display the first and last names of each person... going
          // back to sql
          $sql = "SELECT * ";
          $sql = $sql . "FROM accounts ";
          $sql = $sql . "WHERE utorid='" . $utorid . "'";
          $for_names = $conn->query($sql);  // different variable otherwise will crash
          $names = $for_names->fetch_assoc();
          $first_name = $names["first_name"];
          $last_name = $names["last_name"];
          
          echo "<li class=\"row\">";
            echo "<ul class=\"mark\">";
              // we want to output each row that contains their utorid, material type, what mark they got and out of what mark
              echo "<li class=\"mark_restrict utorid\">" . $utorid . "</li>";
              echo "<li class=\"mark_restrict\">" . $first_name . "</li>";
              echo "<li class=\"mark_restrict\">" . $last_name . "</li>";
              echo "<li class=\"mark_restrict\">" . $content . "</li>";
              echo "<li class=\"mark_restrict\">" . $mark . "</li>";
              echo "<li class=\"mark_restrict\">" . $out_of . "</li>";
              echo "<li class=\"mark_restrict\">" . $weight . "</li></br>";
            echo "</ul>";
          echo "</li>";
          $total += $mark;
          $num_people++;
        }
        $average_marks = number_format($total / $num_people, 2);
        $percentage = number_format($average_marks * 100 / $out_of, 2);
        echo "Class average: " . $average_marks . " / " . $out_of . " (" . $percentage ."%)";
      echo "</ul>";
  echo "</div>";
  }
?>