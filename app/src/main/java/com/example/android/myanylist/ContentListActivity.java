//TODO
// create an icon
// change deletion behaviour from swiping to the right to a swipe menu (button that appears when swiping a certain amount)
// (apparently ultimate recycler can do this)
// alternatively, implement an undo delete snackbar that pops up when you swipe an
// maybe both
// add delete button in drop down on entry page
// allow for modification of image
// -> ensure that mImageRes is updated and then the image view is updated from that
// use a more robust time input and variable type for media creation date
// hide uneccesary (empty) titles and views
// perform small visual tweaks (coloured status header)
// sort recycler view by status
// add extra variables that may be useful
// add rating input (star rating?)
// add back to top functionality with up arrow
// add media type variable (i think thats what you have to do in sqlite i dont 100% remember and im sleepy)
// implement itemtouchhelper.simplecallback onmove to allow rearranging of entries
// clean up this fucking terrible mess of code
// give consistent and easier to understand variable names

package com.example.android.myanylist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.android.myanylist.ContentActivity;
import com.example.android.myanylist.R;
import com.example.android.myanylist.adapters.EntryRecyclerAdapter;
import com.example.android.myanylist.models.MediaEntry;
import com.example.android.myanylist.persistence.EntryRepository;
import com.example.android.myanylist.util.VerticalSpacingItemDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ContentListActivity extends AppCompatActivity implements EntryRecyclerAdapter.OnContentListener, FloatingActionButton.OnClickListener {

    // ui components
    private RecyclerView mRecyclerView;

    // vars (anything that isnt a view or a widget)
    private ArrayList<MediaEntry> mEntries = new ArrayList<>();
    private EntryRecyclerAdapter mEntryRecyclerAdapter;
    private EntryRepository mMediaEntryRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_list);

        // the recycler here just dumps all of the data in the database onto the screen
        // but the aim is to have it sorted planning -> in progress -> completed -> dropped
        // the two ways to handle this appear to be either
        // sort the list in the sqlite query (probably the easiest way to do it but might misbehave?)
        // create four linked recycler views that process each type of element in sequence (this might not work either)
        mRecyclerView = findViewById(R.id.content_recycler_view);       // attach the variable to its id

        mMediaEntryRepository = new EntryRepository(this);
        initRecyclerView();
        retrieveEntries();
//        insertFakeContent();

        Toolbar toolbar = (Toolbar) findViewById(R.id.content_list_toolbar);
        setSupportActionBar(toolbar);
        setTitle("Games");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        findViewById(R.id.list_fab).setOnClickListener(this);
    }

    private void retrieveEntries(){
        // triggers when the observer is attached and on any subsequent changes to the repository
        // this can be used for both initial startup and updating further changes to the databases
        mMediaEntryRepository.retrieveEntryTask().observe(this, new Observer<List<MediaEntry>>() {
            @Override
            public void onChanged(List<MediaEntry> mediaEntries) {
                if(mEntries.size() > 0) {
                    mEntries.clear();
                }
                if(mediaEntries != null){
                    mEntries.addAll(mediaEntries);
                }
                mEntryRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }

    private void insertFakeContent() {
        mEntries.add(new MediaEntry("Dark Souls", 8, "1 jan 2020", 2, "FromSoftware", "Dark Souls takes place in the fictional kingdom of Lordran, where players assume the role of a cursed undedad who begins a pilgrimage to discover the fate of their kind", R.drawable.dark_souls));
        mEntries.add(new MediaEntry("Bloodborne", 10, "2 jan 2020", 1, "FromSoftware", "Bloodborne follows the player's character, a hunter, through the decrepit city of yharnam", R.drawable.bloodborne));
        mEntries.add(new MediaEntry("Sekiro", 9, "3 jan 2020", 0, "FromSoftware", "Sekiro takes place in the sengoku period in japan, and follows a shinobi known as wolf as he attempts to take revenge on a samurai clan who attacked him and kidnapped his lord", R.drawable.sekiro));
        mEntryRecyclerAdapter.notifyDataSetChanged();
    }

    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(16);
        mRecyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(mRecyclerView);
        mEntryRecyclerAdapter = new EntryRecyclerAdapter(mEntries, this);
        mRecyclerView.setAdapter(mEntryRecyclerAdapter);
    }

    @Override
    public void onContentClick(int position) {
        mEntries.get(position);
        Intent intent = new Intent(this, ContentActivity.class);
        intent.putExtra("selected_content", mEntries.get(position));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.list_fab:
                Intent intent = new Intent(this, ContentActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void deleteEntry(MediaEntry entry){
        mEntries.remove(entry);
        mEntryRecyclerAdapter.notifyDataSetChanged();

        mMediaEntryRepository.deleteEntry(entry);
    }

    private ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            deleteEntry(mEntries.get(viewHolder.getAdapterPosition()));
        }
    };


}
